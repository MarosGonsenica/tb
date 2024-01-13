package com.example.job_application.controllers.data;

import com.example.job_application.repositories.CurrencyConnectionToDatabaseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DatabaseRun implements CommandLineRunner {

    private CurrencyConnectionToDatabaseRepository currencyConnectionToDatabaseRepository;
    private ResourceLoader resourceLoader;

    public DatabaseRun(CurrencyConnectionToDatabaseRepository repository, ResourceLoader resourceLoader) {
        this.currencyConnectionToDatabaseRepository = repository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = resourceLoader.getResource("classpath:database.txt").getInputStream();

        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyRate> currencyRates = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        currencyConnectionToDatabaseRepository.saveAll(currencyRates);
    }
}
