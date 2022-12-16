package com.yanalexn.expense_limit_api.service_layer;

import com.yanalexn.expense_limit_api.data_layer.entity.Account;
import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.ExpenseLimitRepository;
import com.yanalexn.expense_limit_api.service_layer.converter.ExpenseLimitInputConverter;
import com.yanalexn.expense_limit_api.service_layer.converter.LimAndTransConverter;
import com.yanalexn.expense_limit_api.service_layer.dto.ExpenseLimitDto;
import com.yanalexn.expense_limit_api.service_layer.dto.ExpenseLimitInput;
import com.yanalexn.expense_limit_api.service_layer.dto.LimAndTransDto;
import com.yanalexn.expense_limit_api.service_layer.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExpenseLimitService {

    private final ExpenseLimitRepository limitRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ExpenseLimitService(ExpenseLimitRepository limitRepository, AccountRepository accountRepository) {
        this.limitRepository = limitRepository;
        this.accountRepository = accountRepository;
    }

    public ExpenseLimit saveExpenseLimit(ExpenseLimitInput input) {

        ExpenseLimit limit = ExpenseLimitInputConverter.inputToExpenseLimit(input);
        limit.setDatetime(OffsetDateTime.now());
        limit.setAccount(accountRepository.findByAccountNumber(input.getAccountNumber()));

        return limitRepository.save(limit);
    }

    public List<LimAndTransDto> findLimAndTransList(Long accountNumber) {

        Long accountId = accountRepository.findByAccountNumber(accountNumber).getAccountNumber();

        List<Object[]> limAndTransList = limitRepository.findLimAndTransList(accountId);

        log.error("findLimAndTransList: {}", limAndTransList);

        List<LimAndTransDto> limAndTransDtoList = new ArrayList<>();

        for (Object[] limAndTrans : limAndTransList) {

            ExpenseLimitDto expenseLimitDto = LimAndTransConverter.arrObjToExpLimDto(limAndTrans);
            TransactionDto transactionDto = LimAndTransConverter.arrObjToTransDto(limAndTrans, accountRepository);

            LimAndTransDto limAndTransDto = LimAndTransDto.builder()
                    .expenseLimitDto(expenseLimitDto)
                    .transactionDto(transactionDto)
                    .build();

            limAndTransDtoList.add(limAndTransDto);
        }

        return limAndTransDtoList;
    }

    public ExpenseLimit ifThereIsNoLimitThisMonthCreateIt(String category, Account account) {

//        log.error("!find last lim this month: {}", limitRepository.findLastLimThisMonth(category, account.getId()).get());

        return limitRepository.findLastLimThisMonth(category, account.getId())
                .orElseGet(() ->
                        limitRepository.save(
                                ExpenseLimit.builder()
                                        .sum(0.)
                                        .currencyShortname("USD")
                                        .datetime(OffsetDateTime.of(
                                                YearMonth.now().atDay(1),
                                                LocalTime.MIN,
                                                ZoneOffset.ofHours(6)))
                                        .expenseCategory(category)
                                        .account(account)
                                        .build()
                        )
                );
    }
}
