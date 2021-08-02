package ru.gb.sklyarov.cloud.server.service.impl;

import lombok.extern.log4j.Log4j2;
import ru.gb.sklyarov.cloud.server.config.PropertyConfig;
import ru.gb.sklyarov.cloud.server.service.StorageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
public class StorageServiceImpl implements StorageService {
    private static StorageServiceImpl storageService;
    private Path storagePath;

    public static StorageService getStorageService() {
        if (storageService == null) {
            storageService = new StorageServiceImpl();
        }
        return storageService;
    }

    public Path getStoragePath() {
        if (storagePath == null) {
            initStorage();
        }
        return storagePath;
    }

    @Override
    public boolean initStorage() {
        storagePath = Paths.get(PropertyConfig.getCloudDirectory());
        try {
            if (Files.notExists(storagePath)) {
                Files.createDirectory(storagePath);
                log.debug("Create directory: " + storagePath.toAbsolutePath());

            }
            return true;
        } catch (IOException e) {
            log.error("Root directory creation error", e);
        }

        return false;
    }

    @Override
    public Path initUserStorage(String login) {
        Path path = null;
        try {
            path = Paths.get(PropertyConfig.getCloudDirectory(), login);
            if (Files.notExists(path)) {
                Files.createDirectory(path);
            }
        } catch (IOException e) {
            log.error("Root directory creation error", e);
        }
        return path;
    }

    @Override
    public boolean isFileExists(File file) {
        return file.exists();
    }

    @Override
    public boolean deleteFile(File file) {
        return file.delete();
    }
}
