package com.yanalexn.expense_limit_api.data_layer.entity;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double sum;

    String currencyShortname;

    OffsetDateTime datetime;

    String expenseCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    Account accountFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    Account accountTo;

    Boolean limitExceeded;

    Double limitRemaining;
}
