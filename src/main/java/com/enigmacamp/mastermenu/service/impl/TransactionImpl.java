package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.dto.request.OrderReq;
import com.enigmacamp.mastermenu.model.dto.request.TransactionDetailReq;
import com.enigmacamp.mastermenu.model.dto.request.TransactionReq;
import com.enigmacamp.mastermenu.model.dto.response.TransactionListRes;
import com.enigmacamp.mastermenu.model.dto.response.TransactionRes;
import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.Employee;
import com.enigmacamp.mastermenu.model.entity.Menu;
import com.enigmacamp.mastermenu.model.entity.Order;
import com.enigmacamp.mastermenu.model.entity.Transaction;
import com.enigmacamp.mastermenu.model.entity.TransactionDetail;
import com.enigmacamp.mastermenu.repository.CustomerRepository;
import com.enigmacamp.mastermenu.repository.EmployeeRepository;
import com.enigmacamp.mastermenu.repository.MenuRepository;
import com.enigmacamp.mastermenu.repository.OrderRepository;
import com.enigmacamp.mastermenu.repository.TransactionRepository;
import com.enigmacamp.mastermenu.service.TransactionDetailService;
import com.enigmacamp.mastermenu.service.TransactionService;
import com.enigmacamp.mastermenu.utils.enums.EOrderStatus;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TransactionListRes> getAllTransaction() {
        List<Transaction> transactions = transactionRepository.getAllTransaction();
        return transactions.stream()
            .map(transaction -> modelMapper.map(transaction,TransactionListRes.class))
            .toList();
    }

    @Override
    public TransactionListRes getTransactionById(String id) {
        Transaction transaction = transactionRepository.getTransactionById(id);
        return modelMapper.map(transaction, TransactionListRes.class);
    }

    @Override
    public List<TransactionListRes> getAllTransactionByCustomerName(String customerName){
        List<Transaction> transactions = transactionRepository.findTransactionsByCustomerName(customerName);

        return transactions.stream()
            .map(transaction-> modelMapper.map(transaction, TransactionListRes.class))
            .toList();
    }

    @Transactional
    @Override
    public TransactionRes createTransaction(TransactionReq transactionReq) {
        Transaction transaction = modelMapper.map(transactionReq, Transaction.class);

        // set date transaction
        transaction.setTransactionDate(Date.valueOf(LocalDate.now()));

        Customer customer = customerRepository.findCustomerByDeletedFalse(transactionReq.getCustomerId());
        
        if(customer == null){
            throw new EntityNotFoundException("Customer with id "+ transactionReq.getCustomerId() +" Not Found");
        }
        
        // set customer
        transaction.setCustomer(customer);

        // set order id
        Order orderCustomer = Order.builder().customer(customer).status(EOrderStatus.PROCESSING).build();
        Order savedOrder = orderRepository.save(orderCustomer);
        transaction.setOrder(savedOrder);

        // set transaction detail
        List<TransactionDetail> transactionDetailList = new ArrayList<>();

        Transaction transactionResult = transactionRepository.save(transaction);

        // set total item, total price
        int totalItem = 0;
        int totalPrice = 0;

        for(TransactionDetailReq transactionDetailReq : transactionReq.getTransactionDetail()){
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setTransaction(transaction);

            // set menu
            Menu menu = menuRepository.findMenuByDeletedFalse(transactionDetailReq.getMenuId());
            
            if(menu == null){
                throw new EntityNotFoundException("Menu with id "+ transactionDetailReq.getMenuId() +" Not Found");
            }

            transactionDetail.setMenu(menu);

            // set quantity
            transactionDetail.setQuantity(transactionDetailReq.getQuantity());
            transactionDetail.setPrice(menu.getPrice());


            if(menu.getStock() == 0 || menu.getStock() < transactionDetailReq.getQuantity()){
                throw new EntityNotFoundException("Stock Not Available");
            } else {
                menu.setStock(menu.getStock() - transactionDetailReq.getQuantity());
            }


            // set subtotal, total item, total price
            int subtotal = menu.getPrice() * transactionDetailReq.getQuantity();
            transactionDetail.setSubtotal(subtotal);
            totalItem += transactionDetailReq.getQuantity();
            totalPrice += subtotal;

            transactionDetailList.add(transactionDetailService.saveTransactionDetail(transactionDetail));
            // update menu
            menuRepository.save(menu);
        }

        transactionResult.setTotalItem(totalItem);
        transactionResult.setTotalPrice(totalPrice);
        transactionResult.setTransactionDetail(transactionDetailList);

        Transaction savedTransaction = transactionRepository.save(transactionResult);

        return modelMapper.map(savedTransaction, TransactionRes.class);

    }

    @Transactional
    public Order updateTransactionByOrder(OrderReq orderReq, Order existingOrder){
        Employee employee = employeeRepository.findEmployeeByDeletedFalse(orderReq.getEmployeeId());
        Transaction transaction = transactionRepository.getTransactionByOrderId(orderReq.getId());
        
        if(orderReq.getStatus() == EOrderStatus.COMPLETED){
            transaction.setEmployee(employee);
        }else if (orderReq.getStatus() == EOrderStatus.CANCELED){
            for (TransactionDetail detail : transaction.getTransactionDetail()) {
                Menu menu = detail.getMenu();
                int quantityToReturn = detail.getQuantity();
                menu.setStock(menu.getStock() + quantityToReturn);  // Kembalikan stok
                menuRepository.save(menu);  // Simpan perubahan stok pada repository
            }
        }

        existingOrder.setCustomer(transaction.getCustomer());
        existingOrder.setStatus(orderReq.getStatus());
        existingOrder.setDate(new java.util.Date());
        
        Order updated = orderRepository.save(existingOrder);
        transactionRepository.save(transaction);

        return updated;
    }

    @Override
    public void deleteTransaction(String id) {
        if(transactionRepository.getTransactionById(id) == null){
            throw new EntityNotFoundException("Transaction Not Found");
        }  
        
        Transaction transaction = transactionRepository.getTransactionById(id);
        EOrderStatus statusTransaction = transaction.getOrder().getStatus();

        if(statusTransaction == EOrderStatus.PROCESSING){
            throw new RuntimeException("Order status still Processing");
        }
        
        transactionRepository.deleteById(id);
        orderRepository.deleteById(transaction.getOrder().getId());
       
    }

    @Transactional
    @Override
    public TransactionRes updateTransaction(TransactionReq transactionReq) {
        Order order = orderRepository.findOrderByDeletedFalse(transactionReq.getOrderId());
        
        if(order == null){
            throw new EntityNotFoundException("Order with id "+ transactionReq.getOrderId() +" Not Found");
        }

        Customer customer = customerRepository.findCustomerByDeletedFalse(transactionReq.getCustomerId());
        
        if(customer == null){
            throw new EntityNotFoundException("Customer with id "+ transactionReq.getCustomerId() +" Not Found");
        }
        
        Employee employee = employeeRepository.findEmployeeByDeletedFalse(transactionReq.getEmployeeId());

        if(employee == null){
            throw new EntityNotFoundException("Employee with id "+ transactionReq.getEmployeeId() +" Not Found");
        }


        if(transactionRepository.getTransactionById(transactionReq.getId()) != null){
            Transaction transactionResult = transactionRepository.getTransactionById(transactionReq.getId());

            transactionResult.setOrder(order);
            transactionResult.setCustomer(customer);
            transactionResult.setEmployee(employee);
            transactionResult.setTransactionDate(Date.valueOf(LocalDate.now()));

            // set transaction detail
            List<TransactionDetail> transactionDetailList = new ArrayList<>();

            // set total item, total price
            int totalItem = 0;
            int totalPrice = 0;

            for(TransactionDetailReq transactionDetailReq : transactionReq.getTransactionDetail()){
                TransactionDetail transactionDetail = transactionDetailService.getTransactionDetailById(transactionDetailReq.getId());
                transactionDetail.setTransaction(transactionResult);

                // set menu
                Menu menu = menuRepository.findMenuByDeletedFalse(transactionDetailReq.getMenuId());
                if(menu == null){
                    throw new EntityNotFoundException("Menu with id "+ transactionDetailReq.getMenuId() +" Not Found");
                }
                
                transactionDetail.setMenu(menu);

                // set quantity
                int oldQuantity = transactionDetail.getQuantity();
                int newMenuStock = menu.getStock() + oldQuantity - transactionDetailReq.getQuantity();

                if(menu.getStock() == 0 || newMenuStock < 0){
                    throw new EntityNotFoundException("Stock Not Available");
                } else {
                    transactionDetail.setQuantity(transactionDetailReq.getQuantity());
                    transactionDetail.setPrice(menu.getPrice());
                    menu.setStock(newMenuStock);
                }

                // set subtotal, total item, total price
                int subtotal = menu.getPrice() * transactionDetailReq.getQuantity();
                transactionDetail.setSubtotal(subtotal);
                totalItem += transactionDetailReq.getQuantity();
                totalPrice += subtotal;

                transactionDetailList.add(transactionDetailService.updateTransactionDetail(transactionDetail));
                // update menu
                menuRepository.save(menu);
            }

            transactionResult.setTotalItem(totalItem);
            transactionResult.setTotalPrice(totalPrice);
            transactionResult.setTransactionDetail(transactionDetailList);

            Transaction updatedTransaction = transactionRepository.save(transactionResult);
            return modelMapper.map(updatedTransaction, TransactionRes.class);
        }else{
            throw new EntityNotFoundException("Transaction Not Found");
        }

    }

    
}
