package com.yanalexn.expense_limit_api.service_layer;

import com.yanalexn.expense_limit_api.data_layer.entity.Account;
import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.data_layer.entity.Transaction;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service_layer.converter.OffsetDateTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;

@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ExpenseLimitService limitService;
    private final KztUsdService kztUsdService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              ExpenseLimitService limitService,
                              KztUsdService kztUsdService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.limitService = limitService;
        this.kztUsdService = kztUsdService;
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

        setLimitRemaining(transaction);
        setLimitExceeded(transaction);

        return transactionRepository.save(transaction);
    }

    private void setLimitRemaining(Transaction transaction) {

        Transaction lastTransaction = transactionRepository.findLastThisMonth(
                transaction.getExpenseCategory(),
                transaction.getAccountFrom().getId()
        ).orElseGet(() -> Transaction.builder()
                .limitRemaining(0.)
                .datetime(OffsetDateTime.of(
                        YearMonth.now().atDay(1),
                        LocalTime.MIN,
                        ZoneOffset.ofHours(6)))
                .build());

        log.error("LAST TRANSACTION WHO ARE YOU: {}", lastTransaction);

        ExpenseLimit limit = limitService.ifThereIsNoLimitThisMonthCreateIt(
                transaction.getExpenseCategory(), transaction.getAccountFrom());

        double exchangeRateIfKzt =
                transaction.getCurrencyShortname().equals("KZT") ? kztUsdService.findToday() : 1;

        double limitSumIfLimitLaterThanLastTrans =
                limit.getDatetime().compareTo(lastTransaction.getDatetime()) >= 0 ? limit.getSum() : 0.;

        log.error("limitSumIfLimitLaterThanLastTrans: {}", limitSumIfLimitLaterThanLastTrans);
        log.error("compare dates: {}", limit.getDatetime().compareTo(lastTransaction.getDatetime()));

        double limitRemaining = limitSumIfLimitLaterThanLastTrans + lastTransaction.getLimitRemaining()
                - transaction.getSum() * exchangeRateIfKzt;

        transaction.setLimitRemaining(limitRemaining);
    }

    private void setLimitExceeded(Transaction transaction) {
        boolean limitExceeded = transaction.getLimitRemaining() < 0;
        transaction.setLimitExceeded(limitExceeded);
    }
}
