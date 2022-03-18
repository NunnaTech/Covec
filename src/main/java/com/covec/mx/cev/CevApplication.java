package com.covec.mx.cev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CevApplication {
	public static void main(String[] args) {
		SpringApplication.run(CevApplication.class, args);
	}
}
