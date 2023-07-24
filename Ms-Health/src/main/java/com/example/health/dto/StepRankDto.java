package com.example.health.dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedNativeQuery(
        name = "getRank",
        query = "select customer_id, step, Rank() over " +
                "( order by step desc, updated_date asc) as rank from health.step_month " +
                " where month = :month limit :limit offset :offset",
        resultSetMapping = "rankDto"
)
@SqlResultSetMapping(name = "rankDto",
        classes = {
                @ConstructorResult(targetClass = StepRankDto.class, columns = {
                        @ColumnResult(name = "customer_id", type = String.class),
                        @ColumnResult(name = "step", type = Integer.class),
                        @ColumnResult(name = "rank", type = BigDecimal.class),
                })
        }
)
public class StepRankDto {

    @Id
    @Column(name = "customer_id")
    String customerId;

    @Transient
    String name;

    Integer step;

    BigDecimal rank;

    public StepRankDto(String customerId, Integer step, BigDecimal rank) {
        this.customerId = customerId;
        this.step = step;
        this.rank = rank;
    }
}
