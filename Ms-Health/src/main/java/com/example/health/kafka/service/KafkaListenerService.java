package com.example.health.kafka.service;

import com.example.health.entity.StepMonth;
import com.example.health.entity.StepWeek;
import com.example.health.service.StepService;
import com.example.health.ulti.KafkaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    @Autowired
    private StepService stepService;

    @KafkaListener(topics = KafkaUtil.KAFKA_TOPIC.STEP_WEEK, groupId = "step-week-id")
    public void listen(StepWeek stepWeek) {
        stepService.saveStepWeek(stepWeek);
    }

    @KafkaListener(topics = KafkaUtil.KAFKA_TOPIC.STEP_MONTH, groupId = "step-month-id")
    public void listen(StepMonth stepMonth) {
        stepService.saveStepMonth(stepMonth);
    }
}
