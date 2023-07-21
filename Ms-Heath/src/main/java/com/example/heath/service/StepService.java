package com.example.heath.service;

import com.example.heath.dto.StepDto;
import com.example.heath.input.StepInput;
import org.springframework.stereotype.Service;

@Service
public interface StepService {

    StepDto addStep(StepInput stepInput);
}
