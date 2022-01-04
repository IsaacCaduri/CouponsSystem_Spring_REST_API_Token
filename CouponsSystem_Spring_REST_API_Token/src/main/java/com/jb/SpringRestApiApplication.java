package com.jb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringRestApiApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext run = SpringApplication.run(SpringRestApiApplication.class, args);
	}

}

