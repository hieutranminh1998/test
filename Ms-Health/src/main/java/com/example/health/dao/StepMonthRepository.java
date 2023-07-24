package com.example.health.dao;

import com.example.health.dto.StepRankDto;
import com.example.health.entity.StepMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.util.List;


@Repository
public interface StepMonthRepository extends JpaRepository<StepMonth, String> {

    StepMonth findByCustomerIdAndMonth(String customerId, String month);

    @Query(nativeQuery = true, name="getRank")
    List<StepRankDto> getListRank(String month, Integer offset, Integer limit);
}
