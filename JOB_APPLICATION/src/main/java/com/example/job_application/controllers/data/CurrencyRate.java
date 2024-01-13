package com.example.job_application.controllers.data;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CurrencyRate {

    @Id
    private String currencyCode;
    private BigDecimal exchangeRate;


}

