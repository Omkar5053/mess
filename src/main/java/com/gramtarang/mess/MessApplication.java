package com.gramtarang.mess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")
public class MessApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessApplication.class, args);
    }

}
