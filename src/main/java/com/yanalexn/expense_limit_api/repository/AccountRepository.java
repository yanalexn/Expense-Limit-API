package com.yanalexn.expense_limit_api.repository;

import com.yanalexn.expense_limit_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}