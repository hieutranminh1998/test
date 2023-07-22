package com.example.health.service.impl;

import com.example.health.dao.StepRepository;
import com.example.health.dto.StepDto;
import com.example.health.dto.CustomerDto;
import com.example.health.entity.Step;
import com.example.health.input.StepInput;
import com.example.health.service.CustomerService;
import com.example.health.service.StepService;
import com.example.health.ulti.AppConstant;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    CustomerService customerService;

    @Autowired
    StepRepository stepRepository;

    @Override
    public StepDto addStep(StepInput stepInput) {
        CustomerDto customerDto = customerService.getCustomer(stepInput.getCustomerId());
        if(customerDto == null){
            throw new ApiException(AppConstant.ERROR.CUSTOMER_NOT_FOUND);
        }
        Step step = getStep(stepInput.getCustomerId(), stepInput.getDate());
        step = setParam(step, stepInput);
        stepRepository.save(step);
        StepDto output = new StepDto();
        BeanUtils.copyProperties(step, output);
        return output;
    }
    @Cacheable(value = "step", key = "#customerId + #date")
    public Step getStep(String customerId, Date date){
        Step step = stepRepository.getByCustomerIdAndDate(customerId, date);
        if(step == null){
            step = new Step();
        }
        return step;
    }

    public Step setParam(Step step, StepInput stepInput){
        BeanUtils.copyProperties(stepInput, step);
        step.setStatus(AppConstant.STEP_STATUS.ACTIVATE);
        step.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return step;
    }

    //    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Override
//    public StepDto addStep(StepInput stepInput) {
//        kafkaTemplate.send("kafka-user-topic", stepInput.getCustomerId());
//        return null;
//    }
}
