package com.yanalexn.expense_limit_api.repository;

import com.yanalexn.expense_limit_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}