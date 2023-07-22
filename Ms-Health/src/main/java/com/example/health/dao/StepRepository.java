package com.example.health.dao;

import com.example.health.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> {


    Step getByCustomerIdAndDate(String customerId, Date date);
}
