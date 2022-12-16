package com.yanalexn.expense_limit_api.web_layer.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.yanalexn.expense_limit_api.data_layer.entity.Transaction;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service_layer.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionMutation implements GraphQLMutationResolver {
    //todo: удали везде лишние импорты
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    @Autowired
    public TransactionMutation(TransactionRepository transactionRepository,
                               AccountRepository accountRepository,
                               TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    public Transaction createTransaction(Double sum, String currencyShortname, String datetime,
                                         String expenseCategory, Long accountFromNumber, Long accountToNumber) {
        Transaction transaction = transactionService.createTransaction(sum, currencyShortname, datetime,
                expenseCategory, accountFromNumber, accountToNumber);

        return transactionRepository.save(transaction);
    }
}
