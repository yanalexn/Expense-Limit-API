package com.yanalexn.expense_limit_api.service.dto;

import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.entity.KztUsd;
import com.yanalexn.expense_limit_api.entity.Transaction;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Dto {//todo: delete this class
    //todo: add DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
    Account account;
    ExpenseLimit expenseLimit;
    Transaction transaction;
    KztUsd kztUsd;
}
