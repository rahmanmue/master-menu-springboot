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
    private final OrderRepository orderRepository;
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
        transaction.setCustomer(this.customerService.getCustomerById(transactionReq.getCustomerId()));

        // set employee
        transaction.setEmployee(this.employeeService.getEmployeeById(transactionReq.getEmployeeId()));

        // set order id
        transaction.setOrder(this.orderRepository.findOrderByStatusCompleted(transactionReq.getOrderId()));

        // set transaction detail
        List<TransactionDetail> transactionDetailList = new ArrayList<>();

        int totalItem = 0;
        int totalPrice = 0;

        for(TransactionDetailReq transactionDetailReq : transactionReq.getTransactionDetail()){
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setTransaction(transaction);

            // set menu
            Menu menu = this.menuService.getMenuById(transactionDetailReq.getMenuId());
            transactionDetail.setMenu(menu);

            // set quantity
            transactionDetail.setQuantity(transactionDetailReq.getQuantity());
            transactionDetail.setPrice(menu.getPrice());


            if(menu.getStock() == 0 || menu.getStock() < transactionDetailReq.getQuantity()){
                throw new RuntimeException("Stock Not Available");
            } else {
                menu.setStock(menu.getStock() - transactionDetailReq.getQuantity());
                Integer subtotal = menu.getPrice() * transactionDetailReq.getQuantity();

                // set subtotal, total item, total price
                transactionDetail.setSubtotal(subtotal);
                totalItem += transactionDetailReq.getQuantity();
                totalPrice += subtotal;

                // update menu
                this.menuService.updateMenu(menu);
            }
            transactionDetailService.saveTransactionDetail(transactionDetail);
            transactionDetailList.add(this.transactionDetailService.saveTransactionDetail(transactionDetail));
        }

        transaction.setTotalItem(totalItem);
        transaction.setTotalPrice(totalPrice);
        transaction.setTransactionDetail(transactionDetailList);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public void deleteTransaction(Transaction transaction) {

    }
}
