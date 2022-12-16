package com.yanalexn.expense_limit_api.web_layer.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.yanalexn.expense_limit_api.data_layer.entity.Transaction;
import com.yanalexn.expense_limit_api.data_layer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
