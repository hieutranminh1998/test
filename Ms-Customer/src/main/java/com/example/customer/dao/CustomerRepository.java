package com.example.customer.dao;

import com.example.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(nativeQuery = true, value = "select * from consumer.customer where customer_id in (:customerIds)")
    List<Customer> findAllByCustomerIds(String[] customerIds);
}
