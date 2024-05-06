package com.portfolio.banktransactions.controller;

import com.portfolio.banktransactions.entity.dto.OperationDTO;
import com.portfolio.banktransactions.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transaction")
@Api(value = "API REST Customer")
@CrossOrigin(origins = "*")
public class TransactionController {


    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ApiOperation(value = "Process Transaction")
    public ResponseEntity<?> processTransaction(@RequestBody OperationDTO operationDTO){
        try {
            log.info("RUNNING TransactionController.processTransaction({})", operationDTO);
            return new ResponseEntity<>(transactionService.processTransaction(operationDTO), HttpStatus.OK);
        }catch (Exception e){
            log.error("ERROR:  TransactionController.processTransaction -> {}", e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/account/{idAccount}")
    @ApiOperation(value = "RETURN list Transactions")
    public ResponseEntity<?> getTransactions(@PathVariable Long idAccount){
        try{
            log.info("RUNNING TransactionController.getTransactions({})", idAccount);
            return new ResponseEntity<>(transactionService.findByAccountId(idAccount), HttpStatus.OK);
        }catch (Exception e){
            log.error("ERROR:  TransactionController.getTransactions -> {}", e.getMessage());
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
