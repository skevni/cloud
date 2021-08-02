package ru.gb.sklyarov.cloud.server.service.impl.command;

import ru.gb.sklyarov.cloud.server.factory.Factory;
import ru.gb.sklyarov.cloud.server.service.AuthorizingService;
import ru.gb.sklyarov.cloud.server.service.CommandService;
import ru.gb.sklyarov.domain.Command;
import ru.gb.sklyarov.domain.CommandType;

public class AuthenticatedCommand implements CommandService {
    private static final int REQUIRED_NUMBER_ARGUMENTS = 2;

    private final AuthorizingService authorizingService;

    public AuthenticatedCommand() {
        this.authorizingService = Factory.getAuthorizingService();
    }

    @Override
    public Command processCommand(Command command) {

        if (command.checkArgs(REQUIRED_NUMBER_ARGUMENTS)) {
            String login = (String) command.getArgs()[0];
            String password = (String) command.getArgs()[1];

            Command resultCommand = new Command();
            if (authorizingService.checkAuthorization(login, password)) {
                resultCommand.setCommandName(CommandType.AUTHENTICATION_SUCCESS);
                resultCommand.setArgs(new Object[]{login});
            } else {
                resultCommand.setCommandName(CommandType.AUTHENTICATION_FAILED);
            }
            return resultCommand;
        }

        return new Command(CommandType.ERROR, new Object[]{"Invalid number of arguments for authentication!"});
    }

    @Override
    public CommandType getCommand() {
        return CommandType.AUTHENTICATION;
    }
}
