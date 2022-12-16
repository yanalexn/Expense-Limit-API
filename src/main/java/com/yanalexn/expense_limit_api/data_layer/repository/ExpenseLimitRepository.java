package com.yanalexn.expense_limit_api.data_layer.repository;

import com.yanalexn.expense_limit_api.data_layer.entity.Account;
import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseLimitRepository extends JpaRepository<ExpenseLimit, Long> {

//    @Query(value =
//            "SELECT * " +
//                    "FROM expense_limit " +
//                    "WHERE account_id = :accountId AND expense_category = :category " +
//                    "ORDER BY datetime DESC " +
//                    "LIMIT 1",
//            nativeQuery = true)
//    Optional<ExpenseLimit> findLast(Long accountId, String category);

    @Query(value = "SELECT e " +
            "FROM ExpenseLimit e " +
            "WHERE e.account = :account AND e.expenseCategory = :category " +
            "ORDER BY e.datetime DESC")
    List<ExpenseLimit> findPrevExpLimits(Account account, String category, PageRequest pageRequest);

//    @Query(value = "SELECT e, t " +
//            "FROM ExpenseLimit e INNER JOIN Transaction t " +
//            "WHERE e.expenseCategory = t.expenseCategory AND e.account = t.accountFrom AND e.datetime < t.datetime " +
//            "AND t.datetime < (SELECT LEAD() OVER  ) ")
//    List<Object[]> findLimsAndTrans(Account account);

//    @Query(value = "SELECT e.id, e.sum, e.currency_shortname, e.datetime, e.expense_category, " +
//            "t.id, t.sum, t.currency_shortname, t.datetime, t.expense_category, t.account_from_id, " +
//            "t.account_to_id, t.limit_exceeded, t.limit_remaining " +
//            "FROM expense_limit e INNER JOIN transaction t " +
//            "ON e.expense_category = t.expense_category " +
//            "    AND DATEDIFF(t.datetime, e.datetime) > 0 " +
//            "    AND (DATEDIFF(( " +
//            "       SELECT e2.datetime " +
//            "       FROM expense_limit e2 " +
//            "        WHERE DATEDIFF(e2.datetime, e.datetime) > 0 AND e.expense_category = e2.expense_category " +
//            "            AND e2.account_id = :accountId " +
//            "        ORDER BY e2.datetime " +
//            "        LIMIT 1 " +
//            "    ), t.datetime) > 0 " +
//            "    OR ( " +
//            "        SELECT e2.datetime " +
//            "        FROM expense_limit e2 " +
//            "        WHERE DATEDIFF(e2.datetime, e.datetime) > 0 AND e.expense_category = e2.expense_category " +
//            "            AND e2.account_id = :accountId " +
//            "        ORDER BY e2.datetime " +
//            "        LIMIT 1 " +
//            "    ) IS NULL) " +
//            "WHERE t.limit_exceeded = TRUE AND e.account_id = :accountId AND t.account_from_id = :accountId "
//    , nativeQuery = true)
//    List<Object[]> findLimsAndTrans(Long accountId);

        @Query(value = "SELECT e.id eid, e.sum esum, e.currency_shortname ecs, e.datetime ed, e.expense_category exc, " +
            "t.id tid, t.sum tsum, t.currency_shortname tcs, t.datetime td, t.expense_category tec, t.account_from_id, " +
            "t.account_to_id, t.limit_exceeded, t.limit_remaining " +
            "FROM expense_limit e INNER JOIN transaction t " +
            "ON e.expense_category = t.expense_category " +
            "    AND DATEDIFF(t.datetime, e.datetime) > 0 " +
            "    AND (DATEDIFF(( " +
            "       SELECT e2.datetime " +
            "       FROM expense_limit e2 " +
            "        WHERE DATEDIFF(e2.datetime, e.datetime) > 0 AND e.expense_category = e2.expense_category " +
            "            AND e2.account_id = :accountId " +
            "        ORDER BY e2.datetime " +
            "        LIMIT 1 " +
            "    ), t.datetime) > 0 " +
            "    OR ( " +
            "        SELECT e2.datetime " +
            "        FROM expense_limit e2 " +
            "        WHERE DATEDIFF(e2.datetime, e.datetime) > 0 AND e.expense_category = e2.expense_category " +
            "            AND e2.account_id = :accountId " +
            "        ORDER BY e2.datetime " +
            "        LIMIT 1 " +
            "    ) IS NULL) " +
            "WHERE t.limit_exceeded = TRUE AND e.account_id = :accountId AND t.account_from_id = :accountId "
    , nativeQuery = true)
    List<Object[]> findLimAndTransList(Long accountId);
}