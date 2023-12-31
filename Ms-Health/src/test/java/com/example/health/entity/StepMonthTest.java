package com.example.health.entity;

import com.example.health.dto.StepRankDto;
import com.example.health.dto.TotalStepWeekDto;
import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StepMonthTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(StepMonth.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));
        assertEquals(new StepMonth("abc", 100,
                null, null, null).getCustomerId(), "abc");
    }
}