package com.example.health.service.impl;

import com.example.health.dto.CustomerDto;
import com.example.health.gRPC.CustomerServiceProxy;
import com.example.health.service.CustomerService;
import com.example.health.ulti.CacheUtil;
import com.example.health.ulti.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerServiceProxy customerServiceProxy;

    @Override
    @Cacheable(value = CacheUtil.CACHE_NAME.CUSTOMER, key = "#customerId")
    public CustomerDto getCustomer(String customerId) {
        GRPCResponse grpcResponse = customerServiceProxy.getCustomer(new GRPCRequest(null, customerId));
        if (grpcResponse == null) {
            return null;
        }
        return JsonUtil.toObject(grpcResponse.getBody(), CustomerDto.class);
    }

    @Override
    public List<CustomerDto> getListCustomer(List<String> customerId) {
        GRPCResponse grpcResponse = customerServiceProxy.getListCustomer(new GRPCRequest(customerId, null));
        if (grpcResponse == null) {
            return null;
        }
        TypeReference<List<CustomerDto>> listTypeReference = new TypeReference<List<CustomerDto>>() {
        };
        return JsonUtil.toObject(grpcResponse.getBody(), listTypeReference);
    }
}
