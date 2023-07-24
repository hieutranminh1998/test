package com.example.customer.service;

import com.example.customer.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    Customer getCustomer(String customerId);

    List<Customer> getListCustomer(String[] ids);
}
