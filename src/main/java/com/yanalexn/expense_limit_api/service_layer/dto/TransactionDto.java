package com.yanalexn.expense_limit_api.service_layer.dto;

import com.yanalexn.expense_limit_api.data_layer.entity.Account;
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
public class TransactionDto implements Serializable {
    private Long id;
    private Double sum;
    private String currencyShortname;
    private String datetime;
    private String expenseCategory;
    private Long accountFromNumber;
    private Long accountToNumber;
    private Boolean limitExceeded;
    private Double limitRemaining;
}