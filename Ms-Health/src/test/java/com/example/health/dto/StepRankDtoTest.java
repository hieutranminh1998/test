package com.example.health.dto;

import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StepRankDtoTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(StepRankDto.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));

        assertEquals(new StepRankDto("abc", "abc", 100,
                BigDecimal.valueOf(1)).getCustomerId(), "abc");
        assertEquals(new StepRankDto("abc", 100,
                BigDecimal.valueOf(1)).getCustomerId(), "abc");
    }
}