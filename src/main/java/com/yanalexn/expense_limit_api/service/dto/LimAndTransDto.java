package com.yanalexn.expense_limit_api.service.dto;

import com.yanalexn.expense_limit_api.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.entity.Transaction;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LimAndTransDto {
    ExpenseLimitDto expenseLimitDto;
    TransactionDto transactionDto;
}
