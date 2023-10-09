package com.cjude.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyExchangeController {
    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repository;
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveCurrencyExchage(@PathVariable String from, @PathVariable String to ){
        //CurrencyExchange currencyExchage = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
        logger.info("retrieveExchangeValue called with {} to {}", from , to);
        var currencyExchange = repository.findByFromAndTo(from, to);
        if (currencyExchange == null){
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }

        String property = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(property);
        return currencyExchange;
    }

}
