package com.example.customer.entity;

import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(Customer.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));

        assertEquals(new Customer("abc", null, null,
                null, null).getCustomerId(), "abc");
    }
}