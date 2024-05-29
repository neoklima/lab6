package commands;

import network.UDPClient;
import requests.ClearRequest;
import utility.Console;
import exceptions.*;

import java.io.IOException;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final Console console;
    private final UDPClient client;

    public Clear(Console console, UDPClient client) {
        super("clear");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду.
     *
     * @param arguments аргументы команды (в данном случае они не используются)
     * @return true, если команда выполнена успешно, в противном случае - false
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            client.sendAndReceiveCommand(new ClearRequest());
            console.println("Коллекция успешно очищена.");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        }
        return false;
    }
}
