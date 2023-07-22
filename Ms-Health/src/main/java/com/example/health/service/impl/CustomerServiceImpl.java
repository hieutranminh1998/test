package com.example.health.service.impl;

import com.example.health.dto.CustomerDto;
import com.example.health.gRPC.CustomerServiceProxy;
import com.example.health.service.CustomerService;
import com.example.health.ulti.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerServiceProxy customerServiceProxy;

    @Override
    @Cacheable(value = "customer", key = "#customerId")
    public CustomerDto getCustomer(String customerId) {
        GRPCResponse grpcResponse = customerServiceProxy.getCustomer(new GRPCRequest(null, customerId));
        if(grpcResponse == null){
            return null;
        }
        return JsonUtil.toObject(grpcResponse.getBody(), CustomerDto.class);
    }
}
