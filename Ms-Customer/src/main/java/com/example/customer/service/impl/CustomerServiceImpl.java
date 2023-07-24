package com.example.customer.service.impl;

import com.example.customer.dao.CustomerRepository;
import com.example.customer.entity.Customer;
import com.example.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(String customerId) {
        Optional<Customer> optional = customerRepository.findById(customerId);
        return optional.orElse(null);
    }

    @Override
    public List<Customer> getListCustomer(String[] ids) {
        return customerRepository.findAllByCustomerIds(ids);
    }
}
