package com.example.health.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "step", schema = "health")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id")
    private String customerId;

    private Integer step;

    private BigDecimal distance;

    private String status;

    @Column(name ="created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private Date date;

    public Step(String customerId, Integer step, BigDecimal distance, String status, Timestamp updatedAt, Date date) {
        this.customerId = customerId;
        this.step = step;
        this.distance = distance;
        this.status = status;
        this.updatedAt = updatedAt;
        this.date = date;
    }
}
