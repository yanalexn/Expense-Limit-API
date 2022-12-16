package com.yanalexn.expense_limit_api.web_layer.controller;

import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.service_layer.ExpenseLimitService;
import com.yanalexn.expense_limit_api.service_layer.dto.ExpenseLimitInput;
import com.yanalexn.expense_limit_api.service_layer.dto.LimAndTransDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ControllerForClient {

    private final ExpenseLimitService limitService;

    public ControllerForClient(ExpenseLimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping("/post")
    public ExpenseLimit post(@RequestBody ExpenseLimitInput input) {
        return limitService.saveExpenseLimit(input);
    }

    @GetMapping("/get/{accountNumber}")
    public List<LimAndTransDto> get(@PathVariable Long accountNumber) {
        return limitService.findLimAndTransList(accountNumber);
    }
}

