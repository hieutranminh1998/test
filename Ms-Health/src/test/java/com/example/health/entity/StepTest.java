package com.example.health.entity;

import com.example.health.dto.StepRankDto;
import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StepTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(Step.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));

        assertEquals(new Step(1, "abc", 100, BigDecimal.valueOf(1), "",
                null, null, null).getCustomerId(), "abc");
        assertEquals(new Step("abc", 100, BigDecimal.valueOf(1), "",
                null, null).getCustomerId(), "abc");
    }
}