package ru.gb.sklyarov.cloud.server.service;


import ru.gb.domain.Command;

public interface CommandDictionaryService {

    String processCommand(Command command);

}
