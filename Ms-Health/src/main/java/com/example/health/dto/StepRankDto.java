package com.example.health.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class StepRankDto {

    @Id
    @Column(name = "customer_id")
    String customerId;

    @Transient
    String name;

    String step;

    Integer rank;
}
