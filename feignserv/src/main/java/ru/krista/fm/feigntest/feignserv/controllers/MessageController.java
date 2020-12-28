package ru.krista.fm.feigntest.feignserv.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.krista.fm.feigntest.feignserv.services.ReceiverService;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/rest")
public class MessageController {

    private final ReceiverService receiverService;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    @GetMapping("/receive")
    public Mono<Void> receive(@RequestParam String message) {
        return Mono.fromRunnable(() -> {
                    receiverService.receiveMessage(message);
                    log.debug("recived " + atomicInteger.getAndIncrement() + "mess:" + message);
                    ////t.success("Message received:" + message);
                });
    }
}
