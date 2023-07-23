package com.example.health.form;

import com.example.health.entity.Step;
import com.google.code.beanmatchers.BeanMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StepFormTest {
    @Test
    public void testTheClassIsGoodJavaBean() {
        MatcherAssert.assertThat(StepForm.class,
                CoreMatchers.allOf(
                        BeanMatchers.hasValidBeanConstructor(),
                        BeanMatchers.hasValidGettersAndSetters()
                ));
    }
}