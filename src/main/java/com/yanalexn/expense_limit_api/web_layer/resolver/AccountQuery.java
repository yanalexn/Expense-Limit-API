package com.yanalexn.expense_limit_api.web_layer.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.yanalexn.expense_limit_api.data_layer.entity.Account;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountQuery implements GraphQLQueryResolver {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountQuery(AccountRepository AccountRepository) {
        this.accountRepository = AccountRepository;
    }

    public Account findAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(null);
    }
}