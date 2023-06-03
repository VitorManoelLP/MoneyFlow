package com.ofx.parse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ParseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParseApplication.class, args);
	}

}
