package commands;

import ask.Ask;
import network.UDPClient;
import utility.Console;
import exceptions.*;
import requests.*;
import responses.*;

import java.io.IOException;

import static ask.Ask.askVehicle;

/**
 * Команда 'remove_lower'. Удаляет из коллекции все элементы, меньшие заданного.
 */
public class RemoveGreater extends Command {
    private final Console console;
    private final UDPClient client;

    public RemoveGreater(Console console, UDPClient client) {
        super("remove_lower");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) {
                throw new WrongAmountOfElementsException();
            }
            console.println("* Введите параметры для элемента, больше которого нужно удалить все элементы:");

            var greaterVehicle = askVehicle(console, 1);
            var request = new RemoveGreaterRequest(greaterVehicle);
            var response = (RemoveGreaterResponse) client.sendAndReceiveCommand(request);

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Все элементы, большие заданного, успешно удалены.");
            return true;

        } catch (WrongAmountOfElementsException e) {
            console.printError(e.getMessage());
            console.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        } catch (Ask.AskBreak e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}