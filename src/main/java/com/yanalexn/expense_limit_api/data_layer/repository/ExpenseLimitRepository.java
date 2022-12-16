package com.yanalexn.expense_limit_api.data_layer.repository;

import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseLimitRepository extends JpaRepository<ExpenseLimit, Long> {

    @Query(value = "SELECT * " +
            "FROM expense_limit " +
            "WHERE YEAR(datetime) = YEAR(CURRENT_DATE()) AND MONTH(datetime) = MONTH(CURRENT_DATE()) " +
            "AND expense_category = :category AND account_id = :id " +
            "ORDER BY datetime DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<ExpenseLimit> findLastLimThisMonth(String category, Long id);

        @Query(value = " SELECT  " +
                "    e.id eid, " +
                "    e.sum esum, " +
                "    e.currency_shortname ecs, " +
                "    e.datetime ed, " +
                "    e.expense_category exc, " +
                "    t.id tid, " +
                "    t.sum tsum, " +
                "    t.currency_shortname tcs, " +
                "    t.datetime td, " +
                "    t.expense_category tec, " +
                "    t.account_from_id, " +
                "    t.account_to_id, " +
                "    t.limit_exceeded, " +
                "    t.limit_remaining " +
                "FROM " +
                "    expense_limit e " +
                "        INNER JOIN " +
                "    transaction t ON e.expense_category = t.expense_category " +
                "        AND e.account_id = t.account_from_id " +
                "WHERE " +
                "    t.limit_exceeded = TRUE " +
                "        AND e.account_id = 2 " +
                "        AND t.datetime > e.datetime " +
                "        AND ((SELECT  " +
                "            e2.datetime " +
                "        FROM " +
                "            expense_limit e2 " +
                "        WHERE " +
                "            e2.datetime > e.datetime " +
                "                AND e.expense_category = e2.expense_category " +
                "                AND e2.account_id = 2 " +
                "        ORDER BY e2.datetime " +
                "        LIMIT 1) > t.datetime " +
                "        OR (SELECT  " +
                "            e2.datetime " +
                "        FROM " +
                "            expense_limit e2 " +
                "        WHERE " +
                "            e2.datetime > e.datetime " +
                "                AND e.expense_category = e2.expense_category " +
                "                AND e2.account_id = 2 " +
                "        ORDER BY e2.datetime " +
                "        LIMIT 1) IS NULL); "
    , nativeQuery = true)
    List<Object[]> findLimAndTransList(Long accountId);
}