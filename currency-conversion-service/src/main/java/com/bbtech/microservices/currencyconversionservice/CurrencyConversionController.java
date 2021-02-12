package com.bbtech.microservices.currencyconversionservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.ConnectException;

@Slf4j
@RestController
public class CurrencyConversionController {

    private CurrencyExchangeProxy exchangeProxy;

    public CurrencyConversionController(CurrencyExchangeProxy exchangeProxy) {
        this.exchangeProxy = exchangeProxy;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calcualteCurrencyConversion( @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        String url = String.format("http://localhost:8000/currency-exchange/from/%s/to/%s", from, to);
        ResponseEntity<CurrencyConversion> responseEntity = null;
        try {
            responseEntity = new RestTemplate().getForEntity(url, CurrencyConversion.class);
        } catch (RestClientException ce) {
            throw new RuntimeException("Unable to connect to the client");
        }
        CurrencyConversion currencyConversion = responseEntity.getBody();
        return CurrencyConversion.builder()
                .id(currencyConversion.getId())
                .from(currencyConversion.getFrom())
                .to(currencyConversion.getTo())
                .quantity(quantity)
                .conversionMultiple(currencyConversion.getConversionMultiple())
                .totalCalcualtedAmount(quantity.multiply(currencyConversion.getConversionMultiple()))
                .environment(currencyConversion.getEnvironment()+" rest template")
                .build();
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calcualteCurrencyConversionFeign( @PathVariable String from,
                                                           @PathVariable String to,
                                                           @PathVariable BigDecimal quantity) {
        CurrencyConversion currencyConversion = exchangeProxy.retrieveCurrencyExchange(from, to);
        return CurrencyConversion.builder()
                .id(currencyConversion.getId())
                .from(currencyConversion.getFrom())
                .to(currencyConversion.getTo())
                .quantity(quantity)
                .conversionMultiple(currencyConversion.getConversionMultiple())
                .totalCalcualtedAmount(quantity.multiply(currencyConversion.getConversionMultiple()))
                .environment(currencyConversion.getEnvironment()+" feign")
                .build();
    }
}
