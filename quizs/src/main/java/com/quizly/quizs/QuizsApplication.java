package com.quizly.quizs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QuizsApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizsApplication.class, args);
	}

}
