package com.yanalexn.expense_limit_api.controller;

import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.entity.KztUsd;
import com.yanalexn.expense_limit_api.entity.Transaction;
import com.yanalexn.expense_limit_api.repository.AccountRepository;
import com.yanalexn.expense_limit_api.repository.ExpenseLimitRepository;
import com.yanalexn.expense_limit_api.repository.KztUsdRepository;
import com.yanalexn.expense_limit_api.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service.dto.Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ControllerForClient {

    private final AccountRepository accountRepository;
    private final ExpenseLimitRepository expenseLimitRepository;
    private final KztUsdRepository kztUsdRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/get")
    public Dto get() {//todo: delete this class
        //todo: add DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
        Account account = Account.builder().accountNumber(1234567890L).build();

        return Dto.builder()
                .account(accountRepository.save(account))
                .expenseLimit(expenseLimitRepository.save(ExpenseLimit.builder().datetime(OffsetDateTime.now()).sum(1234.12).currencyShortname("KZT").expenseCategory("product").account(account).build()))
                .transaction(transactionRepository.save(Transaction.builder().datetime(OffsetDateTime.now()).sum(12.12).currencyShortname("KZT").accountFrom(account).accountTo(account).expenseCategory("product").limitExceeded(false).build()))
                .kztUsd(kztUsdRepository.save(KztUsd.builder().exchangeRate(447.4).datetime(OffsetDateTime.now()).build()))
                .build();
    }
}

