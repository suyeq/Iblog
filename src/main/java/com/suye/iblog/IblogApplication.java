package com.suye.iblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.suye.iblog")
public class IblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(IblogApplication.class, args);
	}
}
