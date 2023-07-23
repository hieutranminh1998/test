package com.example.health.dao;

import com.example.health.dto.StepRankDto;
import com.example.health.entity.Step;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.util.Date;
import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> {


    Step getByCustomerIdAndDate(String customerId, Date date);


    @Query("SELECT sum(m.step) from Step m where m.customerId = ?1 and m.date >= ?2 and m.date <= ?3 and m.status = ?4")
    Integer sumStepInWeek(String customerId, Date startDate, Date endDate, String status);

    @Query(nativeQuery = true, value = "select sum(step) from health.step where customer_id =:customerId" +
            " AND status =:status " +
            " AND extract(YEAR FROM date) = extract(YEAR FROM now())" +
            " AND extract(MONTH FROM date) = extract(MONTH FROM now())")
    Integer sumStepInMonth(String customerId, String status);
}
