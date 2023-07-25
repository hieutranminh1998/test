package com.example.health.kafka.service;

import com.example.health.entity.StepMonth;
import com.example.health.entity.StepWeek;
import com.example.health.service.StepService;
import com.example.health.ulti.JsonUtil;
import com.example.health.ulti.KafkaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);
    @Autowired
    private StepService stepService;
    private String payload;

    @KafkaListener(topics = KafkaUtil.KAFKA_TOPIC.STEP_WEEK, groupId = "step-week-id")
    public void listenStepWeek(String request) {
        LOGGER.info("received payload='{}'", request);
        payload = request;
        StepWeek stepWeek = JsonUtil.toObject(request, StepWeek.class);
        stepService.saveStepWeek(stepWeek);
    }

    @KafkaListener(topics = KafkaUtil.KAFKA_TOPIC.STEP_MONTH, groupId = "step-month-id")
    public void listenStepMonth(String request) {
        LOGGER.info("received payload='{}'", request);
        payload = request;
        StepMonth stepMonth = JsonUtil.toObject(request, StepMonth.class);
        stepService.saveStepMonth(stepMonth);
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
