package com.portfolio.banktransactions.controller;

import com.portfolio.banktransactions.entity.Account;
import com.portfolio.banktransactions.entity.Transaction;
import com.portfolio.banktransactions.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionControllerTest {

    private TransactionController controller;

    @Mock
    private TransactionService service;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        this.controller = new TransactionController(service);
    }


    @Test
    void processTransactionTest() throws Exception {
        Transaction transaction = Transaction.builder()
                .idTransaction(1L)
                .account(new Account())
                .value(BigDecimal.ONE)
                .data(Timestamp.valueOf(LocalDateTime.now()))
                .balanceAfter(BigDecimal.ONE)
                .balanceBefore(BigDecimal.ZERO).build();
        Mockito.when(service.processTransaction(Mockito.any())).thenReturn(transaction);
        ResponseEntity<?> response = controller.processTransaction(Mockito.any());
        Assertions.assertEquals(response.getBody(), transaction);
    }

    @Test
    void processTransactionExceptionTest() throws Exception {
        Mockito.when(service.processTransaction(Mockito.any())).thenThrow(RuntimeException.class);
        ResponseEntity<?> response = controller.processTransaction(Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getAccountsTest() {
        List<Transaction> transaction = new ArrayList<>();
        Mockito.when(service.findByAccountId(Mockito.any())).thenReturn(transaction);
        ResponseEntity<?> response = controller.getTransactions(Mockito.any());
        Assertions.assertEquals(response.getBody(), transaction);
    }

    @Test
    void getAccountsExceptionTest() {
        Mockito.when(service.findByAccountId(Mockito.any())).thenThrow(RuntimeException.class);
        ResponseEntity<?> response = controller.getTransactions(Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
