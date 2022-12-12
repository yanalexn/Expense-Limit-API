package com.yanalexn.expense_limit_api.repository;

import com.yanalexn.expense_limit_api.entity.ExpenseLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseLimitRepository extends JpaRepository<ExpenseLimit, Long> {
}