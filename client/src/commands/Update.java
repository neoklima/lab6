package commands;

import ask.Ask;
import network.UDPClient;
import utility.Console;
import exceptions.*;
import requests.UpdateRequest;
import responses.UpdateResponse;

import java.io.IOException;

import static ask.Ask.askVehicle;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 */
public class Update extends Command {
    private final Console console;
    private final UDPClient client;

    public Update(Console console, UDPClient client) {
        super("update ID {element}");
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

            var id = Integer.parseInt(arguments[1]);

            console.println("* Введите данные обновленного продукта:");
            var updatedVehicle = askVehicle(console, id);
            var request = new UpdateRequest(id, updatedVehicle);
            var response = (UpdateResponse) client.sendAndReceiveCommand(request);
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Продукт успешно обновлен.");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch(IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        } catch (Ask.AskBreak e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
