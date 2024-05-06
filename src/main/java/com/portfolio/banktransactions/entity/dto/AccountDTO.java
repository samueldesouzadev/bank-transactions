package com.portfolio.banktransactions.entity.dto;

import com.portfolio.banktransactions.entity.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountDTO {
    private BigDecimal value;
    private Long number;
    private String name;
    private List<Transaction> transactions;
}
