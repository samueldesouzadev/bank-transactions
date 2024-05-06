package com.portfolio.banktransactions.entity;

import com.portfolio.banktransactions.entity.constants.TypeTransactionEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_TRANSACTION")
    private Long idTransaction;

    @NotNull
    @Column
    private TypeTransactionEnum type;

    @NotNull
    @Column
    private BigDecimal value;

    @NotNull
    @Column
    private BigDecimal balanceBefore;

    @NotNull
    @Column
    private BigDecimal balanceAfter;

    @NotNull
    @Column
    private Timestamp data;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinTable(name="TB_ACCOUNT_TRANSACTION",
            joinColumns={@JoinColumn(name="ID_TRANSACTION",
                    referencedColumnName="ID_TRANSACTION")},
            inverseJoinColumns={@JoinColumn(name="ID_ACCOUNT",
                    referencedColumnName="ID_ACCOUNT")})
    @ToString.Exclude
    private Account account;
}
