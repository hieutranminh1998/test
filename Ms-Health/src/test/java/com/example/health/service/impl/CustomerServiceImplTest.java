package com.example.health.service.impl;

import com.example.health.dto.CustomerDto;
import com.example.health.gRPC.CustomerServiceProxy;
import com.example.health.service.CustomerService;
import com.example.health.ulti.JsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.db.proto.GRPCRequest;
import vn.com.msb.db.proto.GRPCResponse;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerServiceProxy customerServiceProxy;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void getCustomer() {
        String customerId = "abc";
        GRPCRequest grpcRequest = new GRPCRequest();
        grpcRequest.setCustomerId(customerId);
        customerServiceProxy = new CustomerServiceProxy();
        assertNull(customerService.getCustomer(customerId));


        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("abc");
        customerDto.setStatus("abc");
        customerDto.setCustomerId(customerId);
        GRPCResponse grpcResponse = new GRPCResponse();
        grpcResponse.setCode(200);
        grpcResponse.setBody(JsonUtil.toString(customerDto));
        Mockito.when(customerServiceProxy.getCustomer(new GRPCRequest(null, customerId))).thenReturn(grpcResponse);
        assertEquals(customerService.getCustomer(customerId), customerDto);
    }
}