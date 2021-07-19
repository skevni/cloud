package ru.gb.sklyarov.cloud.server.service;

public interface AuthorizingService {

    boolean isLoginBusy(String login);

    boolean userRegistration(String login, String password);

    boolean checkAuthorization(String login, String password);
}
