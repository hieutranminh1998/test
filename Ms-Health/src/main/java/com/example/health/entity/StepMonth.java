package com.example.health.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "step_month", schema = "health")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepMonth {
    
    @Id
    @Column(name = "customer_id")
    private String customerId;

    private Integer step;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private String month;

    private String status;
}
