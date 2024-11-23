package com.enigmacamp.mastermenu.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.enigmacamp.mastermenu.model.entity.Menu;
import com.enigmacamp.mastermenu.model.entity.Transaction;
import com.enigmacamp.mastermenu.model.entity.TransactionDetail;
import com.enigmacamp.mastermenu.repository.MenuRepository;
import com.enigmacamp.mastermenu.repository.TransactionDetailRepository;
import com.enigmacamp.mastermenu.repository.TransactionRepository;

@ActiveProfiles("test")
@DataJpaTest
public class TransactionDetailRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private MenuRepository menuRepository;

    private Transaction transaction;
    private Menu menu;

    @BeforeEach
    void setup(){
        menu = Menu.builder()
                .name("Burger")
                .price(10000)
                .build();
        menuRepository.save(menu);

        transaction = Transaction.builder()
                .transactionDate(new Date())
                .totalPrice(10000)
                .totalItem(1)
                .build();
        transactionRepository.save(transaction);


        TransactionDetail transactionDetail = TransactionDetail.builder()
                                        .transaction(transaction)
                                        .menu(menu)
                                        .price(menu.getPrice())
                                        .quantity(1)
                                        .subtotal(menu.getPrice())
                                        .build();
        transactionDetailRepository.save(transactionDetail);
    }


    @Test
    public void testGetTransactionDetailById(){
        TransactionDetail detail = transactionDetailRepository.getAllTransactionDetail().get(0);
        TransactionDetail result = transactionDetailRepository.getTransactionDetailById(detail.getId());
        assertNotNull(result, "TransactionDetail should not be null");
        assertEquals(detail.getId(), result.getId(), "TransactionDetail ID should match");
    }
}
