package ru.gb.sklyarov.cloud.server.service;

import java.sql.Connection;

public interface DatabaseService {

    void connect();

    void disconnect();

    Connection getConnection();
}
