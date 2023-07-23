package com.example.health.dao;

import com.example.health.dto.StepRankDto;
import com.example.health.entity.StepMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StepMonthRepository extends JpaRepository<StepMonth, String> {

    StepMonth findByCustomerIdAndMonth(String customerId, String month);

    @Query(nativeQuery = true, value = " select customer_id, step, Rank() over " +
            "( order by step desc, updated_date asc) as rank from health.step_month " +
            " where month = :month limit :limit offset :offset")
    List<StepRankDto> getListRank(String month, Integer offset, Integer limit);
}
