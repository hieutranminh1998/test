package com.example.kafka.service;

 
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolderProxy implements ApplicationContextAware {
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;   
    }

    public static ApplicationContext getContext() {
        return context;
    }
}