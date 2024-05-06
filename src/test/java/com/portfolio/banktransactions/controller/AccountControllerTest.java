package com.portfolio.banktransactions.controller;

import com.portfolio.banktransactions.entity.Account;
import com.portfolio.banktransactions.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountControllerTest {

    private AccountController controller;

    @Mock
    private AccountService service;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        this.controller = new AccountController(service);
    }


    @Test
    void getAccountByIdTest(){
        Account account = Account.builder().idAccount(1L).name("Samuel").balance(BigDecimal.ZERO).build();
        Mockito.when(service.findByNumber(Mockito.any())).thenReturn(account);
        ResponseEntity<?> response = controller.getAccountByNumber(1L);
        Assertions.assertEquals(response.getBody(), account);
    }

    @Test
    void getAccountByIdExceptionTest(){
        Mockito.when(service.findByNumber(Mockito.any())).thenThrow(RuntimeException.class);
        ResponseEntity<?> response = controller.getAccountByNumber(1L);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getAccountsTest(){
        List<Account> accounts = new ArrayList<>();
        Mockito.when(service.findAll()).thenReturn(accounts);
        ResponseEntity<?> response = controller.getAccounts();
        Assertions.assertEquals(response.getBody(), accounts);
    }

    @Test
    void getAccountsExceptionTest(){
        Mockito.when(service.findAll()).thenThrow(RuntimeException.class);
        ResponseEntity<?> response = controller.getAccounts();
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
