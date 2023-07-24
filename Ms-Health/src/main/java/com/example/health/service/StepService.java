package com.example.health.service;

import com.example.health.dto.StepDto;
import com.example.health.dto.StepRankDto;
import com.example.health.dto.TotalStepMonthDto;
import com.example.health.dto.TotalStepWeekDto;
import com.example.health.entity.StepMonth;
import com.example.health.entity.StepWeek;
import com.example.health.form.StepForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StepService {

    StepDto addStep(StepForm stepForm);

    TotalStepWeekDto getStepInThisWeek(String customerId);

    TotalStepMonthDto getStepInThisMonth(String customerId);

    void saveStepWeek(StepWeek stepWeek);

    void saveStepMonth(StepMonth stepMonth);


}
