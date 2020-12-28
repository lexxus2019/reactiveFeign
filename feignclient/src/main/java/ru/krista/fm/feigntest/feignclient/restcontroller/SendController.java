package ru.krista.fm.feigntest.feignclient.restcontroller;

import com.google.gson.Gson;
import dtos.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.krista.fm.feigntest.feignclient.feign.MessageMicroservice;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/rest")
public class SendController {

    @Autowired
    private MessageMicroservice dispatcherService;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private final int COUNT = 1000;

    @GetMapping("/send")
    public ResponseEntity<Void> send() throws InterruptedException {

        var task = new Runnable() {
            @Override
            public void run() {
                var name = Thread.currentThread().getName();
                int i;
                do {
                    i = atomicInteger.get();

                    var mess = MessageDto.builder().message("Mess ("+name+"): " + i).time(LocalDateTime.now()).build();
                    var gson = new Gson();
                    System.out.println(i);
                    if (i == COUNT) {
                        dispatcherService.receive(gson.toJson(mess)).subscribe(null, null, () -> atomicInteger.set(COUNT + 1000));
                    }
                    else {
                        dispatcherService.receive(gson.toJson(mess)).block();
                    }
                    i = atomicInteger.incrementAndGet();
                } while (i < COUNT);

                System.out.println("Thread " + name + " ended with counter=" + i);
            }
        };

        var t1 = LocalDateTime.now();
        var thread1 = new Thread(task);

        var thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        do {
            Thread.sleep(100);
        } while (atomicInteger.get() < COUNT + 1000);

        ////Thread.sleep(60000);

        thread1.interrupt();
        thread2.interrupt();

        var t2 = LocalDateTime.now();

        System.out.println(Duration.between(t2, t1));

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/onesend")
    public ResponseEntity<Void> onesend() throws InterruptedException {

        var t1 = LocalDateTime.now();

                var name = Thread.currentThread().getName();
                int i;
                do {
                    i = atomicInteger.get();

                    var mess = MessageDto.builder().message("Mess ("+name+"): " + i).time(LocalDateTime.now()).build();
                    var gson = new Gson();
                    System.out.println(i);
                    if (i == COUNT - 1) {
                        dispatcherService.receive(gson.toJson(mess)).subscribe(null, null, () -> atomicInteger.set(COUNT + 1000));
                        dispatcherService.receive(gson.toJson(mess)).
                    }
                    else {
                        dispatcherService.receive(gson.toJson(mess)).subscribe();
                    }
                    i = atomicInteger.incrementAndGet();
                } while (i < COUNT);

                System.out.println("Thread " + name + " ended with counter=" + i);


        do {
            Thread.sleep(100);
        } while (atomicInteger.get() < COUNT + 1000);


        var t2 = LocalDateTime.now();

        System.out.println(Duration.between(t2, t1));

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
