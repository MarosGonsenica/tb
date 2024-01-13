package com.example.job_application;

import com.example.job_application.controllers.CurrencyConnectionToDatabaseController;
import com.example.job_application.controllers.data.CurrencyRate;
import com.example.job_application.repositories.CurrencyConnectionToDatabaseRepository;
import com.example.job_application.services.CurrencyConnectionToDatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurrencyConnectionToDatabaseController.class)
public class EndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyConnectionToDatabaseService currencyConnectionToDatabaseService;

    @MockBean
    private CurrencyConnectionToDatabaseRepository currencyConnectionToDatabaseRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testConvertCurrencyEndpoint() throws Exception {
        BigDecimal amount = BigDecimal.valueOf(100);
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        BigDecimal expectedConversion = BigDecimal.valueOf(85.00);

        when(currencyConnectionToDatabaseService.convertCurrency(amount, fromCurrency, toCurrency)).thenReturn(expectedConversion);

        mockMvc.perform(get("/api/currencyExchangeRate/convertCurrency/{amount}/{fromCurrency}/{toCurrency}",
                        amount, fromCurrency, toCurrency))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedConversion.toString()));
    }

    @Test
    public void testGetAllEndpoint() throws Exception {
        List<CurrencyRate> rates = Arrays.asList(
                new CurrencyRate("USD", BigDecimal.valueOf(1.0)),
                new CurrencyRate("EUR", BigDecimal.valueOf(0.85))
        );

        when(currencyConnectionToDatabaseRepository.findAll()).thenReturn(rates);

        mockMvc.perform(get("/api/currencyExchangeRate/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].currencyCode").value("USD"))
                .andExpect(jsonPath("$[1].currencyCode").value("EUR"));
    }
}
