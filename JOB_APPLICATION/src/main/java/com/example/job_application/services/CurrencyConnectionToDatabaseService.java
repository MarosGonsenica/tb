package com.example.job_application.services;

import com.example.job_application.controllers.data.CurrencyRate;
import com.example.job_application.repositories.CurrencyConnectionToDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyConnectionToDatabaseService {

    @Autowired
    private CurrencyConnectionToDatabaseRepository currencyConnectionToDatabaseRepository;

    @Autowired
    public CurrencyConnectionToDatabaseService(CurrencyConnectionToDatabaseRepository currencyConnectionToDatabaseRepository) {
        this.currencyConnectionToDatabaseRepository = currencyConnectionToDatabaseRepository;
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrencyCode, String toCurrencyCode) {
        CurrencyRate fromCurrency = currencyConnectionToDatabaseRepository.findByCurrencyCode(fromCurrencyCode);
        CurrencyRate toCurrency = currencyConnectionToDatabaseRepository.findByCurrencyCode(toCurrencyCode);

        BigDecimal exchangeRate = toCurrency.getExchangeRate().divide(fromCurrency.getExchangeRate(), 5, RoundingMode.HALF_UP);
        return amount.multiply(exchangeRate);
    }


}



