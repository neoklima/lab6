package managers;

import commands.CommandServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Управляет командами.
 */
public class CommandManager {
    private final Map<String, CommandServer> commands = new HashMap<>();

    /**
     * Добавляет команду.
     * @param commandName Название команды.
     * @param command Команда.
     */
    public void register(String commandName, CommandServer command) {
        commands.put(commandName, command);
    }

    /**
     * @return Словарь команд.
     */
    public Map<String, CommandServer> getCommands() {
        return commands;
    }
}