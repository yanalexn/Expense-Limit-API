package com.yanalexn.expense_limit_api.web_layer.controller;

import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.ExpenseLimitRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.KztUsdRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service_layer.ExpenseLimitService;
import com.yanalexn.expense_limit_api.service_layer.dto.ExpenseLimitDto;
import com.yanalexn.expense_limit_api.service_layer.dto.LimAndTransDto;
import com.yanalexn.expense_limit_api.service_layer.dto.TransactionDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ControllerForClient {

    private final ExpenseLimitService limitService;

    public ControllerForClient(ExpenseLimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping("/post")
    public ExpenseLimit post(@RequestBody ExpenseLimit limit) {
        return limitService.saveExpenseLimit(limit);
    }

    @GetMapping("/get/{accountId}")
    public List<LimAndTransDto> get(@PathVariable Long accountId) {
        return limitService.findLimAndTransList(accountId);
    }
}

