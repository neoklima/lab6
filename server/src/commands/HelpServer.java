package commands;


import requests.Request;
import responses.*;
import managers.CommandManager;

/**
 * Команда 'help'. Выводит справку по доступным командам
 */
public class HelpServer extends CommandServer {
    private final CommandManager commandManager;

    public HelpServer(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var helpMessage = new StringBuilder();

        commandManager.getCommands().values().forEach(command -> {
            helpMessage.append(" %-35s%-1s%n".formatted(command.getName(), command.getDescription()));
        });

        return new HelpResponse(helpMessage.toString(), null);
    }
}