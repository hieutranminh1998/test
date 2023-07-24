package com.example.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LoadBalanceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoadBalanceServerApplication.class, args);
	}

}
