package com.example.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    String customerId;

    String name;

    String status;

    Timestamp createdAt;

    Timestamp updatedAt;
}
