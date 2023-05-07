package com.lab.snippet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lab")
public class SnippetApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnippetApplication.class, args);
    }
}
