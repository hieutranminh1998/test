package com.example.health.service.impl;

import com.example.health.dao.StepMonthRepository;
import com.example.health.dao.StepRepository;
import com.example.health.entity.Step;
import com.example.health.entity.StepWeek;
import com.example.health.kafka.service.KafkaListenerService;
import com.example.health.kafka.service.KafkaSendService;
import com.example.health.ulti.DateUtil;
import com.example.health.ulti.JsonUtil;
import com.example.health.ulti.KafkaUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StepCacheServiceImplTest {

    @Mock
    StepRepository stepRepository;

    @Mock
    StepMonthRepository stepMonthRepository;

    @Mock
    KafkaSendService kafkaSendService;

    @InjectMocks
    StepCacheServiceImpl stepCacheService;

    @Mock
    CacheManager cacheManager;

    @Mock
    KafkaListenerService kafkaListenerService;


    @Test
    void getStep() {
        String customerId = "abc";
        Date date = new Date();
        Step step = new Step();
        step.setStep(0);
        String dateStr = DateUtil.simpleDateFormatDDMMYY.format(date);
        Mockito.when(stepRepository.getByCustomerIdAndDate(customerId, date)).thenReturn(null);
        assertEquals(stepCacheService.getStep(customerId, date, dateStr), step);

        Mockito.when(stepRepository.getByCustomerIdAndDate(customerId, date)).thenReturn(step);
        assertEquals(stepCacheService.getStep(customerId, date, dateStr), step);


    }

    @Test
    void updateStep() {
        Step step = new Step();
        String dateStr = "";
        assertEquals(stepCacheService.updateStep(step, dateStr), step);
    }

    @Test
    void resetCacheStep() {

        assertTrue(stepCacheService.resetCacheStep());
    }

    @Test
    void getStepInThisWeek() {

        assertEquals(stepCacheService.getStepInThisWeek("abc", new Date(), new Date()), 0);
    }

    @Test
    void updateStepInThisWeek() {
        Step step = new Step();
        step.setStep(10);
        step.setCustomerId("abc");
        String dateStr = DateUtil.simpleDateFormatDDMMYY.format(new Date());
        assertEquals(stepCacheService.updateStepInThisWeek(step, step, dateStr), 0);
    }

    @Test
    void resetCacheStepInThisWeek() {
        assertTrue(stepCacheService.resetCacheStepInThisWeek());
    }

    @Test
    void getStepInThisMonth() {
        assertEquals(stepCacheService.getStepInThisMonth("abc"), 0);
    }

    @Test
    void updateStepInThisMonth() {
        Step step = new Step();
        step.setStep(10);
        step.setCustomerId("abc");
        assertEquals(stepCacheService.updateStepInThisMonth(step, step), 0);
    }

    @Test
    void resetCacheStepInThisMonth() {
        assertTrue(stepCacheService.resetCacheStepInThisMonth());
    }

    @Test
    void getListRank() {
        assertEquals(stepCacheService.getListRank(0, 10), new ArrayList<>());
    }

    @Test
    void resetCacheRank() {
        assertTrue(stepCacheService.resetCacheRank());
    }
}