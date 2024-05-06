package com.portfolio.banktransactions.repository;

import com.portfolio.banktransactions.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM TB_ACCOUNT WHERE NUMBER = ?1", nativeQuery = true)
    Optional<Account> findByNumber(Long number);
}
