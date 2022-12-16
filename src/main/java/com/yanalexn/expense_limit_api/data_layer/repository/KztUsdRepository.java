package com.yanalexn.expense_limit_api.data_layer.repository;

import com.yanalexn.expense_limit_api.data_layer.entity.KztUsd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KztUsdRepository extends JpaRepository<KztUsd, Long> {

    @Query(value = "SELECT * " +
            "FROM kzt_usd " +
            "WHERE DAY(datetime) = DAY(CURRENT_DATE)",
            nativeQuery = true)
    Optional<KztUsd> findToday();
}