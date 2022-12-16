package com.yanalexn.expense_limit_api.data_layer.repository;

import com.yanalexn.expense_limit_api.data_layer.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * " +
            "FROM transaction " +
            "WHERE YEAR(datetime) = YEAR(CURRENT_DATE) AND MONTH(datetime) = MONTH(CURRENT_DATE) " +
            "AND expense_category = :category AND account_from_id = :id " +
            "ORDER BY datetime DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Transaction> findLastThisMonth(String category, Long id);
}