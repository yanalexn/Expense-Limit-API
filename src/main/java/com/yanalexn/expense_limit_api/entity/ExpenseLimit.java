package com.yanalexn.expense_limit_api.entity;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class ExpenseLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double sum;

    String currencyShortname;

    OffsetDateTime datetime;

    String expenseCategory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Account account;
}
