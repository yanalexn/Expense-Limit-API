package com.yanalexn.expense_limit_api.controller;

import com.yanalexn.expense_limit_api.entity.Account;
import com.yanalexn.expense_limit_api.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.entity.KztUsd;
import com.yanalexn.expense_limit_api.entity.Transaction;
import com.yanalexn.expense_limit_api.repository.AccountRepository;
import com.yanalexn.expense_limit_api.repository.ExpenseLimitRepository;
import com.yanalexn.expense_limit_api.repository.KztUsdRepository;
import com.yanalexn.expense_limit_api.repository.TransactionRepository;
import com.yanalexn.expense_limit_api.service.ExpenseLimitService;
import com.yanalexn.expense_limit_api.service.dto.Dto;
import com.yanalexn.expense_limit_api.service.dto.ExpenseLimitDto;
import com.yanalexn.expense_limit_api.service.dto.LimAndTransDto;
import com.yanalexn.expense_limit_api.service.dto.TransactionDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/client")
public class ControllerForClient {

    private final AccountRepository accountRepository;
    private final ExpenseLimitRepository expenseLimitRepository;
    private final KztUsdRepository kztUsdRepository;
    private final TransactionRepository transactionRepository;
    private final ExpenseLimitService limitService;

    public ControllerForClient(AccountRepository accountRepository, ExpenseLimitRepository expenseLimitRepository,
                               KztUsdRepository kztUsdRepository, TransactionRepository transactionRepository,
                               ExpenseLimitService limitService) {
        this.accountRepository = accountRepository;
        this.expenseLimitRepository = expenseLimitRepository;
        this.kztUsdRepository = kztUsdRepository;
        this.transactionRepository = transactionRepository;
        this.limitService = limitService;
    }

    @GetMapping("/get_base")
    public Dto getBasic() {//todo: delete this class
        //todo: add DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
        Account account = Account.builder().accountNumber(1234567890L).build();

        return Dto.builder()
                .account(accountRepository.save(account))
                .expenseLimit(expenseLimitRepository.save(ExpenseLimit.builder().datetime(OffsetDateTime.now()).sum(1234.12).currencyShortname("KZT").expenseCategory("product").account(account).build()))
                .transaction(transactionRepository.save(Transaction.builder().datetime(OffsetDateTime.now()).sum(12.12).currencyShortname("KZT").accountFrom(account).accountTo(account).expenseCategory("product").limitExceeded(false).build()))
                .kztUsd(kztUsdRepository.save(KztUsd.builder().exchangeRate(447.4).datetime(OffsetDateTime.now()).build()))
                .build();
    }

    @PostMapping("/post")
    public ExpenseLimit post(@RequestBody ExpenseLimit limit) {
        return limitService.saveExpenseLimit(limit);
    }

    @GetMapping("/get/{accountId}")
    public List<LimAndTransDto> get(@PathVariable Long accountId) {

        List<Object[]> limAndTransList = expenseLimitRepository.findLimsAndTrans(accountId);
        List<LimAndTransDto> limAndTransDtoList = new ArrayList<>();

        for (Object[] limAndTrans : limAndTransList) {

            LimAndTransDto limAndTransDto;
            ExpenseLimitDto expenseLimitDto;
            TransactionDto transactionDto;

//            ExpenseLimit expenseLimit = (ExpenseLimit) limAndTrans[0];
//            Transaction transaction = (Transaction) limAndTrans[1];

//            expenseLimitDto = ExpenseLimitDto.builder()
//                    .id(expenseLimit.getId())
//                    .sum(expenseLimit.getSum())
//                    .currencyShortname(expenseLimit.getCurrencyShortname())
//                    .expenseCategory(expenseLimit.getExpenseCategory())
//                    .datetime(expenseLimit.getDatetime())
//                    .build();
//
//            transactionDto = TransactionDto.builder()
//                    .id(transaction.getId())
//                    .sum(transaction.getSum())
//                    .currencyShortname(transaction.getCurrencyShortname())
//                    .expenseCategory(transaction.getExpenseCategory())
//                    .datetime(transaction.getDatetime())
//                    .accountFromAccountNumber(transaction.getAccountFrom().getAccountNumber())
//                    .accountToAccountNumber(transaction.getAccountTo().getAccountNumber())
//                    .limitExceeded(transaction.getLimitExceeded())
//                    .limitRemaining(transaction.getLimitRemaining())
//                    .build();

            expenseLimitDto = ExpenseLimitDto.builder()
                    .id(((BigInteger) limAndTrans[0]).longValue())
                    .sum(((BigDecimal) limAndTrans[1]).doubleValue())
                    .currencyShortname((String) limAndTrans[2])
//                    .datetime((...) limAndTrans[3])
                    .expenseCategory((String) limAndTrans[4])
                    .build();

            transactionDto = TransactionDto.builder()
                    .id(((BigInteger) limAndTrans[5]).longValue())
                    .sum(((BigDecimal) limAndTrans[6]).doubleValue())
                    .currencyShortname((String) limAndTrans[7])
//                    .datetime(limAndTrans[8])
                    .expenseCategory((String) limAndTrans[9])
//                    .accountFromAccountNumber(transaction.getAccountFrom().getAccountNumber())
//                    .accountToAccountNumber(transaction.getAccountTo().getAccountNumber())
                    .limitExceeded((boolean) limAndTrans[12])
                    .limitRemaining(((BigDecimal) limAndTrans[13]).doubleValue())
                    .build();

            limAndTransDto = LimAndTransDto.builder()
                    .expenseLimitDto(expenseLimitDto)
                    .transactionDto(transactionDto)
                    .build();

            limAndTransDtoList.add(limAndTransDto);
        }

        return limAndTransDtoList;
    }
}

