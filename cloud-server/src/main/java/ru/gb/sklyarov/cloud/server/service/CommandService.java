package ru.gb.sklyarov.cloud.server.service;

import ru.gb.domain.Command;

public interface CommandService {

    String processCommand(Command command);

    String getCommand();

}
