package com.portfolio.banktransactions.service;

import com.portfolio.banktransactions.entity.Account;
import com.portfolio.banktransactions.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountServiceTest {

    private AccountService service;

    @Mock
    private AccountRepository repository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        this.service = new AccountService(repository);
    }

    @Test
    void findByIdTest(){
        Account account = Account.builder().idAccount(1L).name("Samuel").balance(BigDecimal.ZERO).build();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.ofNullable(account));
        Account response = service.findByNumber(1L);
        Assertions.assertEquals(response, account);
    }

    @Test
    void findAllTest(){
        List<Account> accounts = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(accounts);
        List<Account> response = service.findAll();
        Assertions.assertEquals(response, accounts);
    }

    @Test
    void saveTest(){
        Account accounts = Account.builder().idAccount(1L).name("Samuel").balance(BigDecimal.ZERO).build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(accounts);
        Account response = service.save(Mockito.any());
        Assertions.assertEquals(response, accounts);
    }
}
