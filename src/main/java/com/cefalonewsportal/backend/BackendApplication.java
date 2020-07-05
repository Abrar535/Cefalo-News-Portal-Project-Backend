package com.cefalonewsportal.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.File;

@SpringBootApplication
@CrossOrigin
@EnableScheduling
@EnableCaching
public class BackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);
        new File("src/main/resources/public/images/stories/").mkdirs();

    }

}
