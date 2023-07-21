package com.example.user.service.impl;

import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    @KafkaListener(topics = "kafka-user-topic", groupId = "group-id")
    public void getCustomer(String message) {
        System.out.println("Received Message in group - group-id: " + message);

    }
}
