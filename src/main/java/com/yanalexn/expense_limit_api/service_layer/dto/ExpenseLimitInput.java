package com.yanalexn.expense_limit_api.service_layer.dto;

import lombok.Data;

@Data
public class ExpenseLimitInput {
    Long id;
    Double sum;
    String currencyShortname;
    String expenseCategory;
    Long accountNumber;
}
