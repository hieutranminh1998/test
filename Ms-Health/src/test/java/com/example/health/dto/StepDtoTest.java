package com.example.health.dto;

import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;


class StepDtoTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(StepDto.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));
    }
}