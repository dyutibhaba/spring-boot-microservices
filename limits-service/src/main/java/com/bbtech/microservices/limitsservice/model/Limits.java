package com.bbtech.microservices.limitsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Setter
@Getter
@AllArgsConstructor
public class Limits {

    private int maximum;
    private int minimum;
}
