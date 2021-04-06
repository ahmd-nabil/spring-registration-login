package com.springregistrationlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringRegistrationLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRegistrationLoginApplication.class, args);
    }

}
