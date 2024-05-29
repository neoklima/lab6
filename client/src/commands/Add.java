package commands;

import ask.Ask;
import network.UDPClient;
import network.UDPClient;
import utility.Console;
import exceptions.*;
import requests.*;
import responses.*;

import java.io.IOException;

import static ask.Ask.askVehicle;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final UDPClient client;

    public Add(Console console, UDPClient client) {
        super("add {element}");
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
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            console.println("* Создание нового продукта:");

            var newVehicle = askVehicle(console, 1);
            var request = new AddRequest(newVehicle);
            var response = (AddResponse) client.sendAndReceiveCommand(request);

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Новый продукт с id=" + response.newId + " успешно добавлен!");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
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