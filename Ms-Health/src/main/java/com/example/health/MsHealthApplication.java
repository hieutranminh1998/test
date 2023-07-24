package com.example.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
@EnableScheduling
public class MsHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsHealthApplication.class, args);
	}

	@Configuration
	class RestTemplateConfig {

		// Create a bean for restTemplate to call services
		@Bean
		@LoadBalanced        // Load balance between service instances running at different ports.
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}
}
