package com.portfolio.banktransactions.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ACCOUNT")
    private Long idAccount;

    @NotNull
    @Column
    private Long number;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private BigDecimal balance;
}
