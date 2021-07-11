package ru.gb.sklyarov.cloud.server;

import org.flywaydb.core.Flyway;
import ru.gb.sklyarov.cloud.server.factory.Factory;
import ru.gb.sklyarov.cloud.server.util.PropertyUtil;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        PropertyUtil propertyUtil = Factory.getProperty();
        Map<String, String> properties = propertyUtil.getAllProperties();

        flywayConfigure(properties);

        Factory.getServerService().startServer();
    }

    private static void flywayConfigure(Map<String, String> properties) {
        Flyway flyway = Flyway.configure()
                .dataSource(properties.getOrDefault("db.url", ""), properties.getOrDefault("db.user", "postgres"), properties.getOrDefault("db.password", "postgrespass"))
                .load();
        flyway.migrate();
    }
}
