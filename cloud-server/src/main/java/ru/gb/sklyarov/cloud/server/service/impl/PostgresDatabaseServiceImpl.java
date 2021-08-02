package ru.gb.sklyarov.cloud.server.service.impl;

import lombok.extern.log4j.Log4j2;
import ru.gb.sklyarov.cloud.server.config.PropertyConfig;
import ru.gb.sklyarov.cloud.server.service.DatabaseService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class PostgresDatabaseServiceImpl implements DatabaseService {

    private static PostgresDatabaseServiceImpl databaseService;
    private static Connection connection;

    public static DatabaseService getDatabaseService() {
        if (databaseService == null) {
            databaseService = new PostgresDatabaseServiceImpl();
        }
        return databaseService;
    }

    @Override
    public void connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(PropertyConfig.getDBUrl(), PropertyConfig.getDBUser(), PropertyConfig.getDBPassword());
                log.debug("Successful database connection");
            } catch (SQLException ex) {
                log.error("Unable to connect to database", ex);
            } finally {
                disconnect();
            }
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            log.error("Error while disconnecting from database", ex);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
