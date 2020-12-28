package ru.krista.fm.feigntest.feignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients("ru.krista.fm.feigntest.feignclient.feign")
public class FeignclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignclientApplication.class, args);
    }

}
