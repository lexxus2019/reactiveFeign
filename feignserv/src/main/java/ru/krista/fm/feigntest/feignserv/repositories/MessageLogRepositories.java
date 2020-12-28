package ru.krista.fm.feigntest.feignserv.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.krista.fm.feigntest.feignserv.domain.MessageLog;

public interface MessageLogRepositories extends CrudRepository<MessageLog, Long> {
}
