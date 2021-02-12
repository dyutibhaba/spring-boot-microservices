package com.bbtech.microservices.limitsservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("limits-service")
@Component
@Setter
@Getter
public class Configuration {

    private int maximum;
    private int minimum;
}
