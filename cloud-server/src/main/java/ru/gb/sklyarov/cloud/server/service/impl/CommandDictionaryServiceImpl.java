package ru.gb.sklyarov.cloud.server.service.impl;

import lombok.extern.log4j.Log4j2;
import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.cloud.server.factory.Factory;
import ru.gb.sklyarov.cloud.server.service.CommandDictionaryService;
import ru.gb.sklyarov.cloud.server.service.CommandService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class CommandDictionaryServiceImpl implements CommandDictionaryService {

    private final Map<String, CommandService> commandDictionary;

    public CommandDictionaryServiceImpl() {
        commandDictionary = Collections.unmodifiableMap(getCommonDictionary());
    }

    private Map<String, CommandService> getCommonDictionary() {
        List<CommandService> commandServices = Factory.getCommandServices();

        Map<String, CommandService> commandDictionary = new HashMap<>();
        for (CommandService commandService : commandServices) {
            commandDictionary.put(commandService.getCommand().toString(), commandService);
        }

        return commandDictionary;
    }

    @Override
    public Command processCommand(Command command) {

        if (commandDictionary.containsKey(command.getCommandName())) {
            return commandDictionary.get(command.getCommandName()).processCommand(command);
        }
        log.error("The command " + command.getCommandName() + " does not exists");
        throw new IllegalArgumentException("The command " + command.getCommandName() + " does not exists");
    }

}
