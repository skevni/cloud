package ru.gb.sklyarov.cloud.server.service.impl.command;

import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.domain.FileType;
import ru.gb.sklyarov.cloud.server.service.CommandService;

import java.io.File;
import java.util.Objects;

public class ViewFilesInDirCommand implements CommandService {

    @Override
    public String processCommand(Command command) {
        final int requirementCountArgs = 1;

        if (command.getArgs().length != requirementCountArgs) {
            throw new IllegalArgumentException("Command \"" + getCommand() + "\" is not correct");
        }

        return process(command.getArgs()[0]);
    }

    private String process(String dirPath) {
        File directory = new File(dirPath);

        if (!directory.exists()) {
            return "Directory is not exists";
        }

        StringBuilder builder = new StringBuilder();
        for (File childFile : Objects.requireNonNull(directory.listFiles())) {
            FileType typeFile = getTypeFile(childFile);
            builder.append(childFile.getName()).append(" | ").append(typeFile).append(System.lineSeparator());
        }

        return builder.toString();
    }

    private FileType getTypeFile(File childFile) {
        return childFile.isDirectory() ? FileType.DIR : FileType.FILE;
    }

    @Override
    public String getCommand() {
        return "ls";
    }

}
