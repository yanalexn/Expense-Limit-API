package com.yanalexn.expense_limit_api.data_layer.repository;

import com.yanalexn.expense_limit_api.data_layer.entity.KztUsd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KztUsdRepository extends JpaRepository<KztUsd, Long> {
}