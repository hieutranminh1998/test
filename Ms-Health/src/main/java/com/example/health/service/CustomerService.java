package com.example.health.service;

import com.example.health.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    CustomerDto getCustomer(String customerId);
}
