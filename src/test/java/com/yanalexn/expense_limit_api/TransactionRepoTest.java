package com.yanalexn.expense_limit_api;

import com.yanalexn.expense_limit_api.data_layer.entity.Account;
import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.data_layer.entity.Transaction;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.ExpenseLimitRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service_layer.converter.OffsetDateTimeConverter;
import com.yanalexn.expense_limit_api.service_layer.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
//@SpringBootTest
@ComponentScan("com.yanalexn.expense_limit_api.service_layer")
@DataJpaTest
public class TransactionRepoTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ExpenseLimitRepository limitRepository;

    @Test
    public void areWeUsingH2InTests() {
        assertTrue(transactionRepository.findById(1L).isEmpty());
    }

    @Test
    public void findLast() {

        Account accountFrom = accountRepository.save(new Account());
        Account accountTo = accountRepository.save(new Account());

        OffsetDateTime earlier = OffsetDateTimeConverter.stringToOffsetDateTime("2022-01-01 01:01:01+06");
        OffsetDateTime later = OffsetDateTimeConverter.stringToOffsetDateTime("2022-02-02 02:02:02+06");

        Transaction firstTransaction = Transaction.builder()
                .datetime(earlier)
                .sum(1.1)
                .currencyShortname("KZT")
                .expenseCategory("product")
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .build();
        Transaction lastTransaction = Transaction.builder()
                .datetime(later)
                .sum(2.2)
                .currencyShortname("USD")
                .expenseCategory("service")
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .build();

        transactionRepository.save(firstTransaction);
        transactionRepository.save(lastTransaction);

        //assuming that the last service limit was set in "2022-01-01 01:01:01+06" (=earlier)
        limitRepository.save(ExpenseLimit.builder().expenseCategory("service").datetime(earlier).account(accountFrom).build());

//        assertEquals(lastTransaction, transactionRepository.findLast(1L, "service", earlier));
//        assertEquals(lastTransaction, transactionRepository.findLast(1L, "service").orElseThrow());
//        assertEquals(lastTransaction, transactionRepository.findLast(accountFrom, "service").orElseThrow());
//        assertEquals(lastTransaction, transactionService.findLast(accountFrom, "service").orElseThrow());
    }


}


