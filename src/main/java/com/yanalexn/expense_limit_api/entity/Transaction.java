package com.yanalexn.expense_limit_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double sum;

    String currency_shortname;

    LocalDate dateTime;//todo: LocalDate???+Timezone

    String expenseCategory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Account account_from;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Account account_to;

    Boolean limitExceeded;
}
