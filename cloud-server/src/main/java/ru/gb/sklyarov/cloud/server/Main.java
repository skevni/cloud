package ru.gb.sklyarov.cloud.server;

import ru.gb.sklyarov.cloud.server.config.FlywayConfig;
import ru.gb.sklyarov.cloud.server.factory.Factory;

public class Main {

    public static void main(String[] args) {

        FlywayConfig.migrate();

        Factory.getServerService().startServer();
    }


}
