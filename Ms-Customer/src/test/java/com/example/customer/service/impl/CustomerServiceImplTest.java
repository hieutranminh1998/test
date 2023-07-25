package com.example.customer.service.impl;

import com.example.customer.dao.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    public void getCustomer(){
        assertNull(customerService.getCustomer("abc"));
    }

    @Test
    public void getListCustomer(){
        String [] stringList = new String[1];
        stringList[0] = "a";
        assertEquals(customerService.getListCustomer(stringList), new ArrayList<>());
    }
}