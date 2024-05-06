package com.portfolio.banktransactions.repository;

import com.portfolio.banktransactions.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT T1.*, T3.* FROM TB_TRANSACTION T1 " +
            "INNER JOIN TB_ACCOUNT_TRANSACTION T2 ON T1.ID_TRANSACTION = T2.ID_TRANSACTION " +
            "INNER JOIN TB_ACCOUNT T3 ON T2.ID_ACCOUNT = T3.ID_ACCOUNT " +
            "WHERE T3.NUMBER = ?1 ORDER BY DATA DESC LIMIT 1;", nativeQuery = true)
    Transaction findLastTransactionByNumber(Long number);

    @Query(value = "SELECT T1.*, T3.* FROM TB_TRANSACTION T1 " +
            "INNER JOIN TB_ACCOUNT_TRANSACTION T2 ON T1.ID_TRANSACTION = T2.ID_TRANSACTION " +
            "INNER JOIN TB_ACCOUNT T3 ON T2.ID_ACCOUNT = T3.ID_ACCOUNT " +
            "WHERE T3.NUMBER = ?1 ORDER BY DATA ASC;", nativeQuery = true)
    List<Transaction> findAllByNumberAccount(Long idAccount);
}
