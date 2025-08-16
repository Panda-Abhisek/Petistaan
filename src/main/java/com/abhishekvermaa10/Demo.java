package com.abhishekvermaa10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class Demo {	
	public static void main(String[] args) {
		SpringApplication.run(Demo.class, args);
	}

}
