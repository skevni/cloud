package ru.gb.sklyarov.cloud.server.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig {
    public static void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(PropertyConfig.getDBUrl(), PropertyConfig.getDBUser(), PropertyConfig.getDBPassword())
                .load();
        flyway.migrate();
    }
}
