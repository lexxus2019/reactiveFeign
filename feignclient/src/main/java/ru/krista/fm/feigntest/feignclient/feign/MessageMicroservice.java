package ru.krista.fm.feigntest.feignclient.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "audit", url = "localhost:8080")
@RequestMapping("/rest")
public interface MessageMicroservice {

    @GetMapping("/receive")
    Mono<Void> receive(@RequestParam String message);
}
