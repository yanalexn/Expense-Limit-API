package com.yanalexn.expense_limit_api.service_layer.converter;

import com.yanalexn.expense_limit_api.data_layer.entity.Account;
import com.yanalexn.expense_limit_api.data_layer.entity.Transaction;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import com.yanalexn.expense_limit_api.service_layer.dto.ExpenseLimitDto;
import com.yanalexn.expense_limit_api.service_layer.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

@Slf4j
public class LimAndTransConverter {

    public static ExpenseLimitDto arrObjToExpLimDto(Object[] limAndTrans) {

        long id = ((BigInteger) limAndTrans[0]).longValue();
        double sum = ((BigDecimal) limAndTrans[1]).doubleValue();
        String currencyShortname = (String) limAndTrans[2];
        Timestamp t = (Timestamp) limAndTrans[3];
        String datetime = OffsetDateTimeConverter.offsetDateTimeToString(t.toInstant().atOffset(OffsetDateTime.now().getOffset()));
        String expenseCategory = (String) limAndTrans[4];

        return ExpenseLimitDto.builder()
                .id(id)
                .sum(sum)
                .currencyShortname(currencyShortname)
                .datetime(datetime)
                .expenseCategory(expenseCategory)
                .build();
    }

    public static TransactionDto arrObjToTransDto(Object[] limAndTrans, AccountRepository accountRepository) {

        long id = ((BigInteger) limAndTrans[5]).longValue();
        double sum = ((BigDecimal) limAndTrans[6]).doubleValue();
        String currencyShortname = (String) limAndTrans[7];
        Timestamp t = (Timestamp) limAndTrans[8];
        String datetime = OffsetDateTimeConverter.offsetDateTimeToString(t.toInstant().atOffset(OffsetDateTime.now().getOffset()));
        String expenseCategory = (String) limAndTrans[9];
        long accountFromNumber = accountRepository.findById(((BigInteger) limAndTrans[10]).longValue()).get().getAccountNumber();
        long accountToNumber = accountRepository.findById(((BigInteger) limAndTrans[11]).longValue()).get().getAccountNumber();
        boolean limitExceeded = (boolean) limAndTrans[12];
        double limitRemaining = ((BigDecimal) limAndTrans[13]).doubleValue();

        return TransactionDto.builder()
                .id(id)
                .sum(sum)
                .currencyShortname(currencyShortname)
                .datetime(datetime)
                .expenseCategory(expenseCategory)
                .accountFromNumber(accountFromNumber)
                .accountToNumber(accountToNumber)
                .limitExceeded(limitExceeded)
                .limitRemaining(limitRemaining)
                .build();
    }
}
