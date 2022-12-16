package com.yanalexn.expense_limit_api.service_layer;

import com.yanalexn.expense_limit_api.data_layer.repository.KztUsdRepository;
import org.springframework.stereotype.Service;

@Service
public class KztUsdService {

    private final KztUsdRepository kztUsdRepository;

    public KztUsdService(KztUsdRepository kztUsdRepository) {
        this.kztUsdRepository = kztUsdRepository;
    }

    public double findToday() {
        return kztUsdRepository.findToday().get().getExchangeRate();
    }
}
