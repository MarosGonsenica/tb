package com.example.job_application.controllers;

import com.example.job_application.controllers.data.CurrencyRate;
import com.example.job_application.repositories.CurrencyConnectionToDatabaseRepository;
import com.example.job_application.services.CurrencyConnectionToDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/currencyExchangeRate")
public class CurrencyConnectionToDatabaseController {

    @Autowired
    private CurrencyConnectionToDatabaseService currencyConnectionToDatabaseService;

    @Autowired
    private CurrencyConnectionToDatabaseRepository currencyConnectionToDatabaseRepository;

    @GetMapping("/getAll")
    public List<CurrencyRate> getAll() {
        return currencyConnectionToDatabaseRepository.findAll();
    }

    @GetMapping("/convertCurrency/{amount}/{fromCurrency}/{toCurrency}")
    public ResponseEntity<BigDecimal> convertCurrency(
            @PathVariable BigDecimal amount,
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency) {
        try {
            BigDecimal convertedAmount = currencyConnectionToDatabaseService.convertCurrency(amount, fromCurrency, toCurrency);
            return new ResponseEntity<>(convertedAmount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
