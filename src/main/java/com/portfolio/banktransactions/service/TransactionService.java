package com.portfolio.banktransactions.service;

import com.portfolio.banktransactions.entity.Account;
import com.portfolio.banktransactions.entity.Transaction;
import com.portfolio.banktransactions.entity.constants.TypeTransactionEnum;
import com.portfolio.banktransactions.entity.dto.OperationDTO;
import com.portfolio.banktransactions.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    private final AccountService accountService;

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(AccountService accountService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    public Transaction processTransaction(OperationDTO operationDTO) throws Exception{
        log.info("RUNNING TransactionService.processTransaction({})", operationDTO);
        isFatFinger(operationDTO);
        Account account = accountService.findByNumber(operationDTO.getNumber());
        BigDecimal newBalance = calculateNewBalance(account.getBalance(), operationDTO);

        Transaction transaction = save(Transaction.builder()
                .type(operationDTO.getType())
                .value(operationDTO.getValue())
                .balanceBefore(account.getBalance())
                .balanceAfter(newBalance)
                .data(Timestamp.valueOf(LocalDateTime.now()))
                .account(account).build());

        account.setBalance(newBalance);
        accountService.save(account);

        log.info("RETURN TransactionService.processTransaction: {}", transaction);
        return transaction;
    }
    private void isFatFinger(OperationDTO operationDTO) throws Exception {
        log.info("RUNNING TransactionService.isFatFinger({})", operationDTO);
        long seconds = Long.parseLong(System.getenv("SECONDS"));
        Transaction transaction = transactionRepository.findLastTransactionByNumber(operationDTO.getNumber());
        if(transaction != null) {
            Instant necessaryTime = Instant.now().minus(seconds, ChronoUnit.SECONDS);
            Instant timeTransaction = transaction.getData().toInstant();

            if (timeTransaction.isAfter(necessaryTime)
                    && transaction.getType().equals(operationDTO.getType())
                        && transaction.getValue().compareTo(operationDTO.getValue()) == 0) {
                log.info("RETURN TransactionService.isFatFinger: IS FAT FINGER");
                throw new Exception("Transação já concluída, espere " + (int) seconds + " segundos");
            }
            log.info("RETURN TransactionService.isFatFinger: IS NOT FAT FINGER");
        }
    }

    private BigDecimal calculateNewBalance(BigDecimal currentBalance, OperationDTO operationDTO) throws Exception{
        log.info("RUNNING TransactionService.calculateNewBalance({}, {})",currentBalance, operationDTO);
        BigDecimal newBalance = currentBalance.add(operationDTO.getValue().multiply(new BigDecimal(operationDTO.getType().type)));

        if(newBalance.compareTo(BigDecimal.ZERO) < 0 && !operationDTO.getType().equals(TypeTransactionEnum.DEPOSITO)){
            log.info("RETURN TransactionService.calculateNewBalance: Saldo insuficiente");
            throw new Exception("Saldo insuficiente para realizar um(a) " + operationDTO.getType());
        }

        log.info("RETURN TransactionService.calculateNewBalance: {}", newBalance);
        return newBalance;
    }

    public Transaction save(Transaction transaction){
        log.info("RUNNING TransactionService.save: {}", transaction);
        Transaction value = transactionRepository.save(transaction);
        log.info("RETURN TransactionService.save: {}", value);
        return value;
    }

    public List<Transaction> findByAccountId(Long accountNumber){
        log.info("RUNNING TransactionService.findByAccountId({})", accountNumber);
        List<Transaction> transactions = transactionRepository.findAllByNumberAccount(accountNumber);
        log.info("RETURN TransactionService.findByAccountId: {}", transactions);
        return transactions;
    }

}
