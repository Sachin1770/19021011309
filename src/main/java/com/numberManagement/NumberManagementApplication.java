package com.numberManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NumberManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberManagementApplication.class, args);
	}


		@Bean
		public RestTemplate restTemplate() {
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			factory.setConnectTimeout(500);
			factory.setReadTimeout(500);
			return new RestTemplate(factory);
		}



}
