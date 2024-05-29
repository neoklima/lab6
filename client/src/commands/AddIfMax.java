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
 * Команда 'add_if_max {element}'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 */
public class AddIfMax extends Command {
    private final Console console;
    private final UDPClient client;

    public AddIfMax(Console console, UDPClient client) {
        super("add_if_max {element}");
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
            var request = new AddIfMaxRequest(newVehicle);
            var response = (AddIfMaxResponse) client.sendAndReceiveCommand(request);

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            if (response.wasAdded) {
                console.println("Новый продукт с id=" + response.newId + " успешно добавлен!");
            } else {
                console.println("Значение нового продукта не превышает значение наибольшего элемента в коллекции.");
            }
            return response.wasAdded;

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
