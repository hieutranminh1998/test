package com.example.health.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class StepDto {

    Integer id;

    String customerId;

    Integer step;

    BigDecimal distance;

    Date date;
}
