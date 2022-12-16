package com.yanalexn.expense_limit_api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseLimitDto implements Serializable {
    private Long id;
    private Double sum;
    private String currencyShortname;
    private OffsetDateTime datetime;
    private String expenseCategory;
}