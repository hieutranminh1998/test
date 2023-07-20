package com.example.heath.api.impl;

import com.example.api.base.ServiceTemplate;
import com.example.bean.MambuError;
import com.example.conf.ApplicationContextHolder;
import com.example.heath.dto.StepDto;
import com.example.heath.entity.Customer;
import com.example.heath.input.StepInput;
import com.example.heath.kafka.KafkaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


public class AddStep extends ServiceTemplate<StepInput, StepDto> {
    KafkaUserService kafkaUserService = ApplicationContextHolder.getContext().getBean(KafkaUserService.class);

    @Override
    protected MambuError validator() throws Exception {
        return null;
    }

    @Override
    protected Object process() throws Exception {
//        Customer customer = kafkaUserService.getCustomerInfo(messageSn, request.getCustomerId());
//        System.out.println(customer.getCustomerId());
        return null;
    }

    @Override
    protected StepDto bindDataResponse() throws Exception {
        return null;
    }
}
