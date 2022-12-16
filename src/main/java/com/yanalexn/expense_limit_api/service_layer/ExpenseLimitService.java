package com.yanalexn.expense_limit_api.service_layer;

import com.yanalexn.expense_limit_api.data_layer.entity.Account;
import com.yanalexn.expense_limit_api.data_layer.entity.ExpenseLimit;
import com.yanalexn.expense_limit_api.data_layer.repository.AccountRepository;
import com.yanalexn.expense_limit_api.data_layer.repository.ExpenseLimitRepository;
import com.yanalexn.expense_limit_api.service_layer.converter.LimAndTransConverter;
import com.yanalexn.expense_limit_api.service_layer.dto.ExpenseLimitDto;
import com.yanalexn.expense_limit_api.service_layer.dto.LimAndTransDto;
import com.yanalexn.expense_limit_api.service_layer.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseLimitService {

    private final ExpenseLimitRepository limitRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ExpenseLimitService(ExpenseLimitRepository limitRepository, AccountRepository accountRepository) {
        this.limitRepository = limitRepository;
        this.accountRepository = accountRepository;
    }

    public ExpenseLimit saveExpenseLimit(ExpenseLimit limit) {
        limit.setDatetime(OffsetDateTime.now());
        return limitRepository.save(limit);
    }

    public Optional<ExpenseLimit> findLast(Account account, String category) {

        List<ExpenseLimit> limits = limitRepository.findPrevExpLimits(account, category,
                PageRequest.of(0, 1));

        return limits.isEmpty() ? Optional.empty() : Optional.of(limits.get(0));
    }

    public List<LimAndTransDto> findLimAndTransList(Long accountId){

        List<Object[]> limAndTransList = limitRepository.findLimAndTransList(accountId);
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
}
