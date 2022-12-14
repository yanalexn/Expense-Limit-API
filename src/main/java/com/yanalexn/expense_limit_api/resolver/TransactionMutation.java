package com.yanalexn.expense_limit_api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.Transaction;
import com.yanalexn.expense_limit_api.repository.AccountRepository;
import com.yanalexn.expense_limit_api.repository.TransactionRepository;
import liquibase.pro.packaged.T;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@Component
public class TransactionMutation implements GraphQLMutationResolver {
    //todo: удали везде лишние импорты
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionMutation(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction createTransaction(Double sum, String currencyShortname,
//                                         OffsetDateTime dateTime,
                                         String datetime,
                                         String expenseCategory, Long accountFromNumber, Long accountToNumber) {
//        log.error("hi!");
        Transaction transaction = new Transaction();
        Account accountFrom = accountRepository.findByAccountNumber(accountFromNumber);
        Account accountTo = accountRepository.findByAccountNumber(accountToNumber);
        try {
            transaction = Transaction.builder()
                    .sum(sum)
                    .currencyShortname(currencyShortname)
//                .datetime(dateTime)
                    .expenseCategory(expenseCategory)
                    .build();
        } catch (Exception e) {
            log.error("smth goes wrong while creating transaction");
        }
        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);

//        log.error("after catch! + {}", transaction);

        return transactionRepository.save(transaction);
    }
}
