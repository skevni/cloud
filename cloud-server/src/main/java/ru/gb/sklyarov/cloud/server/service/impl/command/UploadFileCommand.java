package ru.gb.sklyarov.cloud.server.service.impl.command;

import ru.gb.sklyarov.cloud.server.service.CommandService;
import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.domain.CommandType;
import ru.gb.sklyarov.domain.FileType;

public class UploadFileCommand implements CommandService {
    @Override
    public Command processCommand(Command command) {
        return null;
    }

    @Override
    public CommandType getCommand() {
        return CommandType.UPLOAD;
    }
}
