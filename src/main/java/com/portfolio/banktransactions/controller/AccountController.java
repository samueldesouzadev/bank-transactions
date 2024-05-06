package com.portfolio.banktransactions.controller;

import com.portfolio.banktransactions.entity.Account;
import com.portfolio.banktransactions.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/account")
@Api(value = "API REST Customer")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{number}")
    @ApiOperation(value = "RETURN ACCOUNT by ID")
    public ResponseEntity<?> getAccountByNumber(@PathVariable Long numberAccount){
        try{
            log.info("RUNNING AccountController.getAccountByNumber({})", numberAccount);
            return new ResponseEntity<>(accountService.findByNumber(numberAccount), HttpStatus.OK);
        }catch (Exception e){
            log.error("ERROR:  AccountController.getAccountByNumber -> {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "RETURN all Accounts ")
    public ResponseEntity<?> getAccounts(){
        try{
            log.info("RUNNING AccountController.getAccounts()");
            return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            log.error("ERROR:  AccountController.getAccounts -> {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
