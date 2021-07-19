package ru.gb.sklyarov.cloud.client.service;

import ru.gb.sklyarov.domain.Command;

public interface NetworkService {

    void sendCommand(Command command);

    String readCommandResult();

    void closeConnection();

}
