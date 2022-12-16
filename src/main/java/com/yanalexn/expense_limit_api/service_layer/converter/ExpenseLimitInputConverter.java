package com.yanalexn.expense_limit_api.service_layer.converter;

import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.service_layer.dto.ExpenseLimitInput;

public class ExpenseLimitInputConverter {

    public static ExpenseLimit inputToExpenseLimit(ExpenseLimitInput input) {
        return ExpenseLimit.builder()
                .sum(input.getSum())
                .currencyShortname(input.getCurrencyShortname())
                .expenseCategory(input.getExpenseCategory())
                .build();
    }
}
