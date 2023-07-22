package com.example.health.service;

import com.example.health.dto.StepDto;
import com.example.health.input.StepInput;
import org.springframework.stereotype.Service;

@Service
public interface StepService {

    StepDto addStep(StepInput stepInput);
}
