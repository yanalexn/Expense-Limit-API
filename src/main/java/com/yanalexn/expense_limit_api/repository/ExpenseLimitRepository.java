package com.yanalexn.expense_limit_api.repository;

import com.yanalexn.expense_limit_api.entity.ExpenseLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExpenseLimitRepository extends JpaRepository<ExpenseLimit, Long> {

    @Query(value =
            "SELECT * " +
                    "FROM expense_limit " +
                    "WHERE account_id = :accountId AND expense_category = :category " +
                    "ORDER BY datetime DESC " +
                    "LIMIT 1",
            nativeQuery = true)
    Optional<ExpenseLimit> findLast(Long accountId, String category);
}