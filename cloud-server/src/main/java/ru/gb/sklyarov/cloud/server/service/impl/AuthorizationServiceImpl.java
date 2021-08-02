package ru.gb.sklyarov.cloud.server.service.impl;

import lombok.extern.log4j.Log4j2;
import ru.gb.sklyarov.cloud.server.factory.Factory;
import ru.gb.sklyarov.cloud.server.service.AuthorizingService;
import ru.gb.sklyarov.cloud.server.service.StorageService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class AuthorizationServiceImpl implements AuthorizingService {

    private final StorageService storageService;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public AuthorizationServiceImpl() {
        this.connection = Factory.getDatabaseService().getConnection();
        this.storageService = Factory.getStorageService();
    }

    @Override
    public boolean isLoginBusy(String login) {
        String isBusySQL = "SELECT user_id FROM users WHERE login = ?;";
        try {
            connection.prepareStatement(isBusySQL);
            preparedStatement.setString(1, login);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            log.error("Error in the busy login check method", ex);
        } finally {
            closePreStatement();
        }
        return false;
    }

    @Override
    public boolean userRegistration(String login, String password) {
        String addUserSQL = "INSERT INTO users(login, password) VALUES(?,?);";
        try {
            connection.prepareStatement(addUserSQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            if (preparedStatement.executeUpdate() > 0) {
                if (isInitUsersDirectory(login)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            log.error("Error in registration method", ex);
        } finally {
            closePreStatement();
        }
        return false;
    }

    private boolean isInitUsersDirectory(String login) {
        return storageService.initUserStorage(login) != null;
    }

    @Override
    public boolean checkAuthorization(String login, String password) {
        String checkAuthSQL = "SELECT user_id FROM users WHERE login = ? AND password = ?;";
        try {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery(checkAuthSQL)) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            log.error("Error in authorization method", ex);
        } finally {
            closePreStatement();
        }
        return false;
    }

    private void closePreStatement() {
        try {
            preparedStatement.close();
        } catch (SQLException ex) {
            log.error("Error when closing connection", ex);
        }
    }
}
