package com.example.health.service;

import com.example.health.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    CustomerDto getCustomer(String customerId);

    List<CustomerDto> getListCustomer(List<String> customerId);
}
