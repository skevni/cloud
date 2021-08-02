package ru.gb.sklyarov.cloud.server;

import ru.gb.sklyarov.cloud.server.config.FlywayConfig;
import ru.gb.sklyarov.cloud.server.factory.Factory;

public class ServerApplication {

    public static void main(String[] args) {

        FlywayConfig.migrate();

        Factory.getDatabaseService().connect();

        Factory.getStorageService().initStorage();

        Factory.getServerService().startServer();
    }
}
