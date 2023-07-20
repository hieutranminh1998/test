package com.example.user.api.impl;

import com.example.api.base.ServiceTemplate;
import com.example.bean.MambuError;
import com.example.user.entity.Customer;

public class GetCustomerInfo extends ServiceTemplate<String, Customer> {
    @Override
    protected MambuError validator() throws Exception {
        return null;
    }

    @Override
    protected Object process() throws Exception {
        return null;
    }

    @Override
    protected Customer bindDataResponse() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId("abc");
        return customer;
    }
}
