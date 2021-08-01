package ru.gb.sklyarov.cloud.client.service;

import ru.gb.sklyarov.domain.Command;

public interface NetworkService {

    void sendCommand(Command command);

    void sendFile(String path);

    void downloadFile(String path);

    void closeConnection();

}
