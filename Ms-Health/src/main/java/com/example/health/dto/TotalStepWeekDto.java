package com.example.health.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TotalStepWeekDto {

    String customerId;

    int step;

    String week;
}
