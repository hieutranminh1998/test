package com.example.health.dao;

import com.example.health.entity.StepWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StepWeekRepository extends JpaRepository<StepWeek, String> {

    StepWeek findByCustomerIdAndDate(String customerId, String date);
}
