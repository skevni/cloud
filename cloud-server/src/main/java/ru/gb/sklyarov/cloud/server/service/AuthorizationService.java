package ru.gb.sklyarov.cloud.server.service;

public interface AuthorizationService {

    boolean checkUsername(String username);

    boolean userRegistration();
}
