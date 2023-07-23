package com.example.health.kafka.service;

import com.example.health.entity.StepMonth;
import com.example.health.entity.StepWeek;
import com.example.health.ulti.JsonUtil;
import com.example.health.ulti.KafkaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSendService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(StepWeek stepWeek) {
        kafkaTemplate.send(KafkaUtil.KAFKA_TOPIC.STEP_WEEK, JsonUtil.toString(stepWeek));
    }

    public void send(StepMonth stepMonth) {
        kafkaTemplate.send(KafkaUtil.KAFKA_TOPIC.STEP_MONTH, JsonUtil.toString(stepMonth));
    }
}
