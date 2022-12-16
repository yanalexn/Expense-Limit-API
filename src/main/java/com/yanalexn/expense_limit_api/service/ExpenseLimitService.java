package com.yanalexn.expense_limit_api.service;

import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.entity.Transaction;
import com.yanalexn.expense_limit_api.repository.ExpenseLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseLimitService {

    private final ExpenseLimitRepository limitRepository;

    @Autowired
    public ExpenseLimitService(ExpenseLimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    public ExpenseLimit saveExpenseLimit(ExpenseLimit limit) {
        limit.setDatetime(OffsetDateTime.now());
        return limitRepository.save(limit);
    }

    public Optional<ExpenseLimit> findLast(Account account, String category) {

        List<ExpenseLimit> limits = limitRepository.findPrevExpLimits(account, category,
                PageRequest.of(0, 1));

        return limits.isEmpty() ? Optional.empty() : Optional.of(limits.get(0));
    }
}
