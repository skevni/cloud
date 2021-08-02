package ru.gb.sklyarov.cloud.server.service.impl.command;

import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.domain.CommandType;
import ru.gb.sklyarov.domain.FileInfo;
import ru.gb.sklyarov.domain.FileType;
import ru.gb.sklyarov.cloud.server.service.CommandService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewFilesInDirCommand implements CommandService {

    @Override
    public Command processCommand(Command command) {
        final int requirementCountArgs = 1;

        if (command.getArgs().length != requirementCountArgs) {
            throw new IllegalArgumentException("Command \"" + getCommand() + "\" is not correct");
        }

        return new Command(CommandType.LS, new Object[]{process((String) command.getArgs()[0])});
    }

    private List<FileInfo> process(String dirPath) {
        List<FileInfo> files = new ArrayList<>();
        File directory = new File(dirPath);

        if (!directory.exists()) {
            return files;
        }

        for (File childFile : Objects.requireNonNull(directory.listFiles())) {
            files.add(new FileInfo(childFile.toPath()));
        }

        return files;
    }

    private FileType getTypeFile(File childFile) {
        return childFile.isDirectory() ? FileType.DIR : FileType.FILE;
    }

    @Override
    public CommandType getCommand() {
        return CommandType.LS;
    }

}
