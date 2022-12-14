package com.yanalexn.expense_limit_api.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.Transaction;
import com.yanalexn.expense_limit_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFieldResolver implements GraphQLResolver<Account> {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountFieldResolver(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

//    public Account getAccountFrom(Transaction transaction) {
//        return accountRepository.findById(transaction.getAccountFrom().getId()).orElseThrow(null);
//    }
//
//    public Account getAccountTo(Transaction transaction) {
//        return accountRepository.findById(transaction.getAccountTo().getId()).orElseThrow(null);
//    }
}
