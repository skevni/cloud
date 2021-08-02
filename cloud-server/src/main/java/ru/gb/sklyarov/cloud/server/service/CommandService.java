package ru.gb.sklyarov.cloud.server.service;

import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.domain.CommandType;

public interface CommandService {

    Command processCommand(Command command);

    CommandType getCommand();

}
