package com.yanalexn.expense_limit_api.service_layer;

import com.yanalexn.expense_limit_api.data_layer.entity.Account;
import com.yanalexn.expense_limit_api.data_layer.entity.Transaction;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.ExpenseLimitRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service_layer.converter.OffsetDateTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ExpenseLimitRepository limitRepository;
    private final ExpenseLimitService limitService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              ExpenseLimitRepository limitRepository,
                              ExpenseLimitService limitService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.limitRepository = limitRepository;
        this.limitService = limitService;
    }

    public Transaction createTransaction(Double sum, String currencyShortname, String datetime,
                                         String expenseCategory, Long accountFromNumber, Long accountToNumber) {

        OffsetDateTime offsetDateTime = OffsetDateTimeConverter.stringToOffsetDateTime(datetime);
        Account accountFrom = accountRepository.findByAccountNumber(accountFromNumber);
        Account accountTo = accountRepository.findByAccountNumber(accountToNumber);

        Transaction transaction = Transaction.builder()
                .sum(sum)
                .currencyShortname(currencyShortname)
                .datetime(offsetDateTime)
                .expenseCategory(expenseCategory)
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .build();

        setLimitExceededAndRemaining(transaction);

        return transactionRepository.save(transaction);
    }

    private void setLimitExceededAndRemaining(Transaction transaction) {

        Long accountFromId = transaction.getAccountFrom().getId();
        Account accountFrom = transaction.getAccountFrom();
        String category = transaction.getExpenseCategory();
        double sum = transaction.getSum();

//        Optional<Transaction> prevTransaction = transactionRepository.findLast(accountFrom, category);
        Optional<Transaction> prevTransaction = findLast(accountFrom, category);

//        log.error("previous transaction + {} + {} + {}", prevTransaction.get().getId(),
//                prevTransaction.get().getLimitRemaining(), prevTransaction.get().getLimitExceeded());

//        prevTransaction.ifPresentOrElse(
//                prev -> transaction.setLimitRemaining(
//                        prev.getLimitRemaining() - sum),
//                () -> limitRepository.findLast(accountFromId, category).ifPresentOrElse(
//                        limit -> transaction.setLimitRemaining(limit.getSum() - sum),
//                        () -> transaction.setLimitRemaining(-sum))
//        );
        prevTransaction.ifPresentOrElse(
                prev -> transaction.setLimitRemaining(
                        prev.getLimitRemaining() - sum),
                () -> limitService.findLast(accountFrom, category).ifPresentOrElse(
                        limit -> transaction.setLimitRemaining(limit.getSum() - sum),
                        () -> transaction.setLimitRemaining(-sum))
        );

        transaction.setLimitExceeded(transaction.getLimitRemaining() < 0);
    }

    public Optional<Transaction> findLast(Account account, String category) {

        List<Transaction> transactions = transactionRepository.findPrevTransactions(account, category,
                PageRequest.of(0, 1));

        return transactions.isEmpty() ? Optional.empty() : Optional.of(transactions.get(0));
    }
}
