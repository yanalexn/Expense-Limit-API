package com.yanalexn.expense_limit_api.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.yanalexn.expense_limit_api.entity.Transaction;
import com.yanalexn.expense_limit_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionQuery implements GraphQLQueryResolver {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionQuery(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction findTransaction(Long id) {
        return transactionRepository.findById(id).orElseThrow(null);
    }
}
