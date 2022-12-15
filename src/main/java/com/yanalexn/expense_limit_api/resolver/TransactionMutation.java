package com.yanalexn.expense_limit_api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.Transaction;
import com.yanalexn.expense_limit_api.repository.AccountRepository;
import com.yanalexn.expense_limit_api.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service.TransactionService;
import liquibase.pro.packaged.T;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

import static com.yanalexn.expense_limit_api.service.OffsetDateTimeConverter.offsetDateTimeToString;
import static com.yanalexn.expense_limit_api.service.OffsetDateTimeConverter.stringToOffsetDateTime;

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
