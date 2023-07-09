package com.nl.abn.bt;

import com.nl.abn.bt.exception.RestExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class GamesAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamesAppApplication.class, args);
    }


}
