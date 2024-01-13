package com.example.job_application;

import com.example.job_application.controllers.data.CurrencyRate;
import com.example.job_application.repositories.CurrencyConnectionToDatabaseRepository;
import com.example.job_application.services.CurrencyConnectionToDatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class ConvertCurrencyTest {

    @Mock
    private CurrencyConnectionToDatabaseRepository currencyConnectionToDatabaseRepository;

    @InjectMocks
    private CurrencyConnectionToDatabaseService currencyConnectionToDatabaseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvertCurrency() {
        CurrencyRate usdRate = new CurrencyRate("USD", BigDecimal.valueOf(1.0));
        CurrencyRate eurRate = new CurrencyRate("EUR", BigDecimal.valueOf(0.85));

        when(currencyConnectionToDatabaseRepository.findByCurrencyCode("USD")).thenReturn(usdRate);
        when(currencyConnectionToDatabaseRepository.findByCurrencyCode("EUR")).thenReturn(eurRate);

        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal expected = BigDecimal.valueOf(85.00000).setScale(5);

        BigDecimal result = currencyConnectionToDatabaseService.convertCurrency(amount, "USD", "EUR");
        assertEquals(expected, result);
    }


}

