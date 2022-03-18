package com.covec.mx.cev;

import com.covec.mx.cev.entities.usuario.enlace.Enlace;
import com.covec.mx.cev.entities.usuario.enlace.EnlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CevApplication {
	public static void main(String[] args) {
		SpringApplication.run(CevApplication.class, args);
	}
}
