package ru.krista.fm.feigntest.feignserv.services;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.krista.fm.feigntest.feignserv.domain.MessageLog;
import ru.krista.fm.feigntest.feignserv.dtos.MessageDto;
import ru.krista.fm.feigntest.feignserv.repositories.MessageLogRepositories;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiverService {

    private final MessageLogRepositories repositories;

    public void receiveMessage(String message) {
        var time = LocalDateTime.now();

        var gson = new Gson();
        var mess =  gson.fromJson(message, MessageDto.class);

        var rec = MessageLog.builder()
                .message(mess.getMessage())
                .startTransactionTime(time)
                .sendTime(mess.getTime())
                .build();

        var saved = repositories.save(rec);

        saved.setEndTransactionTime(LocalDateTime.now());
        repositories.save(saved);

        log.debug("Received message: " + message);
    }
}
