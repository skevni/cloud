package ru.gb.sklyarov.cloud.server.service;


import ru.gb.sklyarov.domain.Command;

public interface CommandDictionaryService {

    String processCommand(Command command);

}
