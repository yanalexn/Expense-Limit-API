package com.yanalexn.expense_limit_api;

import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.entity.Transaction;
import com.yanalexn.expense_limit_api.repository.AccountRepository;
import com.yanalexn.expense_limit_api.repository.ExpenseLimitRepository;
import com.yanalexn.expense_limit_api.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service.ExpenseLimitService;
import com.yanalexn.expense_limit_api.service.OffsetDateTimeConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("com.yanalexn.expense_limit_api.service")
public class ExpenseLimitRepoTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ExpenseLimitRepository limitRepository;

    @Autowired
    ExpenseLimitService limitService;

    @Test
    public void findLast() {

        Account account = accountRepository.save(new Account());

        OffsetDateTime earlier = OffsetDateTimeConverter.stringToOffsetDateTime("2022-01-01 01:01:01+06");
        OffsetDateTime later = OffsetDateTimeConverter.stringToOffsetDateTime("2022-02-02 02:02:02+06");

        ExpenseLimit firstLimit = ExpenseLimit.builder()
                .datetime(earlier)
                .expenseCategory("service")
                .account(account)
                .build();
        ExpenseLimit lastLimit = ExpenseLimit.builder()
                .datetime(later)
                .expenseCategory("service")
                .account(account)
                .build();

        limitRepository.save(firstLimit);
        limitRepository.save(lastLimit);

//        assertEquals(lastLimit, limitRepository.findLast(1L, "service").orElseThrow());
        assertEquals(lastLimit, limitService.findLast(account, "service").orElseThrow());
    }
}
