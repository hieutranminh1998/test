package com.example.customer.service;

import com.example.customer.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    Customer getCustomer(String customerId);
}
