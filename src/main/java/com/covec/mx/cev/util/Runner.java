package com.covec.mx.cev.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Runner implements CommandLineRunner{

    @Autowired
    PasswordEncoder passwordEncoder;
    

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        String password = "123456";
        System.out.println(passwordEncoder.encode(password));
    }
    
}
