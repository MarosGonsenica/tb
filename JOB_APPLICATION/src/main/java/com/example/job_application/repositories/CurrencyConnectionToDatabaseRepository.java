package com.example.job_application.repositories;

import com.example.job_application.controllers.data.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CurrencyConnectionToDatabaseRepository extends JpaRepository<CurrencyRate, String> {

    CurrencyRate findByCurrencyCode(String currencyCode);

    List<CurrencyRate> findAll();
}
