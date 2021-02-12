package com.bbtech.microservices.limitsservice.controller;

import com.bbtech.microservices.limitsservice.config.Configuration;
import com.bbtech.microservices.limitsservice.model.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public Limits getAllLimits() {

        return new Limits(configuration.getMaximum(), configuration.getMinimum());
    }
}
