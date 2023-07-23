package com.example.health.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalStepMonthDto {

    String customerId;

    int step;

    String month;
}
