package com.portfolio.banktransactions.entity.dto;

import com.portfolio.banktransactions.entity.constants.TypeTransactionEnum;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OperationDTO {
    private TypeTransactionEnum type;
    private BigDecimal value;
    private Long number;
}
