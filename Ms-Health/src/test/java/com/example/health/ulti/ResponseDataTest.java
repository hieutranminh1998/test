package com.example.health.ulti;

import com.example.health.dto.StepRankDto;
import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;


class ResponseDataTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(ResponseData.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));
    }
}