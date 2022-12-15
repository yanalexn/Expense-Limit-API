package com.yanalexn.expense_limit_api.repository;

import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//    @Query(value =
//            "SELECT * " +
//                    "FROM transaction " +
//                    "WHERE account_from_id = :id AND expense_category = :category AND DATEDIFF(datetime, :last_limit_datetime) >= 0 " +
//                    "ORDER BY datetime DESC " +
//                    "LIMIT 1",
//            nativeQuery = true)
//    Transaction findLast(Long id, String category, @Param("last_limit_datetime") OffsetDateTime lastLimitDatetime);

//    @Query(value =
//            "SELECT * " +
//                    "FROM transaction " +
//                    "WHERE account_from_id = :id AND expense_category = :category AND DATEDIFF(s, :lastLimitDate, datetime) >= 0 " +
//                    "ORDER BY datetime DESC " +
//                    "LIMIT 1",
//            nativeQuery = true)
//    Transaction findLast(Long id, String category, OffsetDateTime lastLimitDate);

//    @Query(value =
//            "SELECT * " +
//                    "FROM transaction " +
//                    "WHERE account_from_id = :id AND expense_category = :category " +
//                    "AND datediff(s, (SELECT MAX(datetime) FROM expense_limit WHERE account_id = :id AND expense_category = :category), datetime) >= 0 " +
//                    "ORDER BY datetime DESC " +
//                    "LIMIT 1",
//            nativeQuery = true)
//    Optional<Transaction> findLast(Long id, String category);

//    @Query(value =
//            "SELECT * " +
//                    "FROM transaction " +
//                    "WHERE account_from_id = :id AND expense_category = :category " +
//                    "AND datediff((SELECT MAX(datetime) FROM expense_limit WHERE account_id = :id AND expense_category = :category), datetime) >= 0 " +
//                    "ORDER BY datetime DESC " +
//                    "LIMIT 1",
//            nativeQuery = true)
//    Optional<Transaction> findLast(Long id, String category);

    @Query(value = "SELECT t " +
            "FROM Transaction t " +
            "WHERE t.accountFrom = :account AND t.expenseCategory = :category " +
            "AND ((SELECT  MAX(e.datetime) FROM ExpenseLimit e WHERE e.account = :account AND e.expenseCategory = :category) IS NULL " +
            "OR (SELECT MAX(e.datetime) FROM ExpenseLimit e WHERE e.account = :account AND e.expenseCategory = :category) < t.datetime)" +
            "ORDER BY t.datetime DESC")
    List<Transaction> findPrevTransactions(Account account, String category, PageRequest pageRequest);




//    @Query(value =
//            "SELECT * " +
//                    "FROM transaction " +
//                    "WHERE account_from_id = :id AND expense_category = :category " +
//                    "ORDER BY datetime DESC " +
//                    "LIMIT 1",
//            nativeQuery = true)
//    Optional<Transaction> findLast(Long id, String category);
}