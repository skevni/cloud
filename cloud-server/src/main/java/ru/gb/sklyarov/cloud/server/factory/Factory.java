package ru.gb.sklyarov.cloud.server.factory;

import ru.gb.sklyarov.cloud.server.network.NettyServerService;
import ru.gb.sklyarov.cloud.server.service.CommandDictionaryService;
import ru.gb.sklyarov.cloud.server.service.CommandService;
import ru.gb.sklyarov.cloud.server.service.DatabaseService;
import ru.gb.sklyarov.cloud.server.service.ServerService;
import ru.gb.sklyarov.cloud.server.service.impl.CommandDictionaryServiceImpl;
import ru.gb.sklyarov.cloud.server.service.impl.PostgresDatabaseServiceImpl;
import ru.gb.sklyarov.cloud.server.service.impl.command.ViewFilesInDirCommand;

import java.util.Collections;
import java.util.List;


public class Factory {

    public static ServerService getServerService() {
        return new NettyServerService();
    }

    public static CommandDictionaryService getCommandDirectoryService() {
        return new CommandDictionaryServiceImpl();
    }

    public static List<CommandService> getCommandServices() {
        return Collections.singletonList(new ViewFilesInDirCommand());
    }

    public static DatabaseService initializeDatabaseService(){
        return new PostgresDatabaseServiceImpl();
    }
}
