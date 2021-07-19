package ru.gb.sklyarov.cloud.server.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.gb.sklyarov.cloud.server.config.PropertyConfig;
import ru.gb.sklyarov.cloud.server.service.DatabaseService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDatabaseServiceImpl implements DatabaseService {
    private static final Logger log = LogManager.getLogger(PostgresDatabaseServiceImpl.class);

    private static Connection connection;

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

    public static Connection getConnection() {
        return connection;
    }
}
