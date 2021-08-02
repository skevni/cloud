package ru.gb.sklyarov.cloud.server.service;

import java.io.File;
import java.nio.file.Path;

public interface StorageService {
    boolean initStorage();

    Path initUserStorage(String login);

    boolean isFileExists(File file);

    boolean deleteFile(File file);
}
