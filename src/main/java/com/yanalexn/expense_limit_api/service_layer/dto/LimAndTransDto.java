package com.yanalexn.expense_limit_api.service_layer.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LimAndTransDto {
    ExpenseLimitDto expenseLimitDto;
    TransactionDto transactionDto;
}
