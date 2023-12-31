package com.example.health.service;

import com.example.health.dto.StepRankDto;
import com.example.health.entity.Step;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface StepCacheService {

    Step getStep(String customerId, Date date, String dateStr);

    Step updateStep(Step step, String dateStr);

    boolean resetCacheStep();

    Integer getStepInThisWeek(String customerId, Date startDate, Date endDate);

    Integer updateStepInThisWeek(Step step, Step oldStep, String dateStr);

    boolean resetCacheStepInThisWeek();

    Integer getStepInThisMonth(String customerId);

    Integer updateStepInThisMonth(Step step, Step oldStep);

    boolean resetCacheStepInThisMonth();

    List<StepRankDto> getListRank(Integer offset, Integer limit);
}
