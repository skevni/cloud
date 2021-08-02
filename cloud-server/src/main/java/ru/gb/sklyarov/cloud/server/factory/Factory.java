package ru.gb.sklyarov.cloud.server.factory;

import ru.gb.sklyarov.cloud.server.network.NettyServerService;
import ru.gb.sklyarov.cloud.server.service.*;
import ru.gb.sklyarov.cloud.server.service.impl.AuthorizationServiceImpl;
import ru.gb.sklyarov.cloud.server.service.impl.CommandDictionaryServiceImpl;
import ru.gb.sklyarov.cloud.server.service.impl.PostgresDatabaseServiceImpl;
import ru.gb.sklyarov.cloud.server.service.impl.StorageServiceImpl;
import ru.gb.sklyarov.cloud.server.service.impl.command.AuthenticatedCommand;
import ru.gb.sklyarov.cloud.server.service.impl.command.DownloadFileCommand;
import ru.gb.sklyarov.cloud.server.service.impl.command.UploadFileCommand;
import ru.gb.sklyarov.cloud.server.service.impl.command.ViewFilesInDirCommand;

import java.util.Arrays;
import java.util.List;

public class Factory {

    public static ServerService getServerService() {
        return new NettyServerService();
    }

    public static CommandDictionaryService getCommandDirectoryService() {
        return new CommandDictionaryServiceImpl();
    }

    public static List<CommandService> getCommandServices() {
        return Arrays.asList(new ViewFilesInDirCommand(),
                new AuthenticatedCommand(),
                new DownloadFileCommand(),
                new UploadFileCommand());
    }

    public static StorageService getStorageService(){
        return StorageServiceImpl.getStorageService();
    }

    public static DatabaseService getDatabaseService() {
        return PostgresDatabaseServiceImpl.getDatabaseService();
    }

    public static AuthorizingService getAuthorizingService(){
        return new AuthorizationServiceImpl();
    }
}
