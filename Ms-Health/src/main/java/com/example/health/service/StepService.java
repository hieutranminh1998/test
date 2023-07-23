package com.example.health.service;

import com.example.health.dto.StepDto;
import com.example.health.form.StepForm;
import org.springframework.stereotype.Service;

@Service
public interface StepService {

    StepDto addStep(StepForm stepForm);


}
