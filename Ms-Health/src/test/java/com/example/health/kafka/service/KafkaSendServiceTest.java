package com.example.health.kafka.service;

import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;

class KafkaSendServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;



}