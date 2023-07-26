package com.example.health.service.impl;

import com.example.health.dao.StepMonthRepository;
import com.example.health.dao.StepRepository;
import com.example.health.dao.StepWeekRepository;
import com.example.health.dto.CustomerDto;
import com.example.health.dto.StepDto;
import com.example.health.entity.Step;
import com.example.health.entity.StepWeek;
import com.example.health.form.StepForm;
import com.example.health.service.CustomerService;
import com.example.health.service.StepCacheService;
import com.example.health.service.StepService;
import com.example.health.ulti.AppConstant;
import com.example.health.ulti.DateUtil;
import org.apache.kafka.common.errors.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class StepServiceImplTest {

    @Mock
    CustomerService customerService;

    @Mock
    StepCacheService stepCacheService;

    @Mock
    StepRepository stepRepository;

    @Mock
    StepWeekRepository stepWeekRepository;

    @Mock
    StepMonthRepository stepMonthRepository;

    @InjectMocks
    StepServiceImpl stepService;

    @Test
    void addStep() {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        StepForm stepForm = new StepForm(100, "abc", BigDecimal.valueOf(20), new Date());
        CustomerDto customerDto = new CustomerDto("abc", "abc", "active", null, null);

        Step step = new Step(1, "abc", 100,
                BigDecimal.valueOf(20), AppConstant.STEP_STATUS.ACTIVATE, time, time, new Date());


        Mockito.when(customerService.getCustomer(stepForm.getCustomerId())).thenReturn(null);
        assertThrows(ApiException.class, () -> {stepService.addStep(stepForm);});

        String dateStr = DateUtil.simpleDateFormatDDMMYY.format(stepForm.getDate());
        Mockito.when(stepCacheService.getStep(stepForm.getCustomerId(), stepForm.getDate(), dateStr)).thenReturn(step);
        Mockito.when(customerService.getCustomer(stepForm.getCustomerId())).thenReturn(customerDto);
        StepDto output = new StepDto(1, "abc", 100, BigDecimal.valueOf(20), new Date());
        assertEquals(stepService.addStep(stepForm).getCustomerId(), output.getCustomerId());
    }

    @Test
    void getStepInThisWeek(){
        String customerId = "";
        CustomerDto customerDto = new CustomerDto("abc", "abc", "active", null, null);
        assertThrows(ApiException.class, () -> {stepService.getStepInThisWeek(customerId);});

        String otherCustomerId = "abc";
        Mockito.when(customerService.getCustomer(otherCustomerId)).thenReturn(null);
        assertThrows(ApiException.class, () -> {stepService.getStepInThisWeek(otherCustomerId);});

        Mockito.when(customerService.getCustomer(otherCustomerId)).thenReturn(customerDto);

        Date startDate = DateUtil.getMondayThisWeek();
        Date endDate = DateUtil.getSundayThisWeek();
        Mockito.when(stepCacheService.getStepInThisWeek(otherCustomerId, startDate, endDate)).thenReturn(200);
        assertEquals(stepService.getStepInThisWeek(otherCustomerId).getStep(), 200);
    }

    @Test
    void getStepInThisMonth(){
        String customerId = "";
        CustomerDto customerDto = new CustomerDto("abc", "abc", "active", null, null);
        assertThrows(ApiException.class, () -> {stepService.getStepInThisMonth(customerId);});

        String otherCustomerId = "abc";
        Mockito.when(customerService.getCustomer(otherCustomerId)).thenReturn(null);
        assertThrows(ApiException.class, () -> {stepService.getStepInThisMonth(otherCustomerId);});

        Mockito.when(customerService.getCustomer(otherCustomerId)).thenReturn(customerDto);

        Mockito.when(stepCacheService.getStepInThisMonth(otherCustomerId)).thenReturn(200);
        assertEquals(stepService.getStepInThisMonth(otherCustomerId).getStep(), 200);
    }
    @Test
    void saveStepWeek(){
        StepWeek stepWeek = new StepWeek("abc", 200, null, null, null);

    }

    @Test
    void saveStepMonth(){

    }

    @Test
    void getLinkRank(){

    }

    @Test
    void setParamStep(){

    }

    @Test
    void setParamStepWeek(){

    }

    @Test
    void setParamStepMonth(){

    }

}