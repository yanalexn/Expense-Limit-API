package com.yanalexn.expense_limit_api.service_layer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseLimitDto implements Serializable {
    private Long id;
    private Double sum;
    private String currencyShortname;
    private String datetime;
    private String expenseCategory;
}