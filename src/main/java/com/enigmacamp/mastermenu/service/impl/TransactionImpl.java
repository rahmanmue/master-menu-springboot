package com.enigmacamp.mastermenu.service.impl;

import com.enigmacamp.mastermenu.model.entity.Customer;
import com.enigmacamp.mastermenu.model.entity.Menu;
import com.enigmacamp.mastermenu.model.entity.Transaction;
import com.enigmacamp.mastermenu.model.entity.TransactionDetail;
import com.enigmacamp.mastermenu.model.request.TransactionDetailReq;
import com.enigmacamp.mastermenu.model.request.TransactionReq;
import com.enigmacamp.mastermenu.repository.*;
import com.enigmacamp.mastermenu.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.getAllTransaction();
    }

    @Override
    public Transaction getTransactionById(String id) {
        return transactionRepository.getTransactionById(id);
    }

    @Override
    public Transaction createTransaction(TransactionReq transactionReq) {
        Transaction transaction = new Transaction();

        // set date transaction
        transaction.setTransactionDate(Date.valueOf(LocalDate.now()));

        // set customer
        transaction.setCustomer(customerService.getCustomerById(transactionReq.getCustomerId()));

        // set employee
        transaction.setEmployee(employeeService.getEmployeeById(transactionReq.getEmployeeId()));

        // set order id
        transaction.setOrder(orderService.getOrderById(transactionReq.getOrderId()));

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
            Menu menu = menuService.getMenuById(transactionDetailReq.getMenuId());
            transactionDetail.setMenu(menu);

            // set quantity
            transactionDetail.setQuantity(transactionDetailReq.getQuantity());
            transactionDetail.setPrice(menu.getPrice());


            if(menu.getStock() == 0 || menu.getStock() < transactionDetailReq.getQuantity()){
                throw new RuntimeException("Stock Not Available");
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
            menuService.updateMenu(menu);
        }

        transactionResult.setTotalItem(totalItem);
        transactionResult.setTotalPrice(totalPrice);
        transactionResult.setTransactionDetail(transactionDetailList);

        return transactionRepository.save(transactionResult);

    }

    @Override
    public Transaction updateTransaction(TransactionReq transactionReq) {
        if(transactionRepository.getTransactionById(transactionReq.getId()) != null){
            Transaction transactionResult = transactionRepository.getTransactionById(transactionReq.getId());
            transactionResult.setOrder(orderService.getOrderById(transactionReq.getOrderId()));
            transactionResult.setCustomer(customerService.getCustomerById(transactionReq.getCustomerId()));
            transactionResult.setEmployee(employeeService.getEmployeeById(transactionReq.getEmployeeId()));
            transactionResult.setTransactionDate(Date.valueOf(LocalDate.now()));

            // set transaction detail
            List<TransactionDetail> transactionDetailList = new ArrayList<>();

            // set total item, total price
            int totalItem = 0;
            int totalPrice = 0;

            for(TransactionDetailReq transactionDetailReq : transactionReq.getTransactionDetail()){
                System.out.println("transaction_detail_id : " +transactionDetailReq.getId());
                TransactionDetail transactionDetail = transactionDetailService.getTransactionDetailById(transactionDetailReq.getId());
                transactionDetail.setTransaction(transactionResult);

                // set menu
                Menu menu = menuService.getMenuById(transactionDetailReq.getMenuId());
                transactionDetail.setMenu(menu);

                // set quantity
                int oldQuantity = transactionDetail.getQuantity();
                int newMenuStock = menu.getStock() + oldQuantity - transactionDetailReq.getQuantity();

                if(menu.getStock() == 0 || newMenuStock < 0){
                    throw new RuntimeException("Stock Not Available");
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
                menuService.updateMenu(menu);
            }

            transactionResult.setTotalItem(totalItem);
            transactionResult.setTotalPrice(totalPrice);
            transactionResult.setTransactionDetail(transactionDetailList);
            return transactionRepository.save(transactionResult);
        }else{
            throw new RuntimeException("Transaction Not Found");
        }

    }

    @Override
    public void deleteTransaction(String id) {
        if(transactionRepository.getTransactionById(id) != null){
            transactionRepository.deleteById(id);
        }else{
            throw new RuntimeException("Transaction Not Found");
        }
    }
}
