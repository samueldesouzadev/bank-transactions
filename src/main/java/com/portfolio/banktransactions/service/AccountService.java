package com.portfolio.banktransactions.service;

import com.portfolio.banktransactions.entity.Account;
import com.portfolio.banktransactions.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findByNumber(Long numberAccount){
        log.info("RUNNING AccountService.findByNumber({})", numberAccount);
        Optional<Account> account = accountRepository.findByNumber(numberAccount);
        log.info("RETURN AccountService.findByNumber: {}", account);
        return account.orElse(null);
    }

    public List<Account> findAll(){
        log.info("RUNNING AccountService.findAll()");
        List<Account> accounts = accountRepository.findAll();
        log.info("RETURN AccountService.findAll: {}", accounts);
        return accounts;
    }

    public Account save(Account account){
        log.info("RUNNING AccountService.save({})", account);
        Account value = accountRepository.save(account);
        log.info("RETURN AccountService.save: {}", value);
        return value;
    }
}
