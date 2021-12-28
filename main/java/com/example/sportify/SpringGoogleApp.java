package com.example.sportify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
public abstract class SpringGoogleApp {
    @GetMapping
    public String welcome() {
        return "Welcome to Google !!";
    }
}

