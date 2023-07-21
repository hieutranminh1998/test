package com.example.heath.service.impl;

import com.example.heath.dto.StepDto;
import com.example.heath.entity.Customer;
import com.example.heath.gRPC.UserService;
import com.example.heath.input.StepInput;
import com.example.heath.service.StepService;
import com.example.heath.ulti.AppConstant;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;
import vn.com.msb.db.proto.user.UserServiceGrpc;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    UserService userService;

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Override
//    public StepDto addStep(StepInput stepInput) {
//        kafkaTemplate.send("kafka-user-topic", stepInput.getCustomerId());
//        return null;
//    }


    @Override
    public StepDto addStep(StepInput stepInput) {

        GRPCResponse grpcResponse = userService.getCustomer(new GRPCRequest(null, stepInput.getCustomerId()));
        if(grpcResponse ==  null){
            throw new ApiException(AppConstant.ERROR.CUSTOMER_NOT_FOUND);
        }
        return null;
    }
}
