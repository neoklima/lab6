package commands;

import network.UDPClient;
import utility.Console;
import exceptions.*;
import requests.*;
import responses.*;

import java.io.IOException;

/**
 * Команда 'sum_of_number_of_wheels'. Выводит сумму значений поля numberOfWheels для всех элементов коллекции.
 */
public class SumOfNumberOfWheels extends Command {
    private final Console console;
    private final UDPClient client;

    public SumOfNumberOfWheels(Console console, UDPClient client) {
        super("sum_of_number_of_wheels");
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
            var request = new SumOfNumberOfWheelsRequest();
            var response = (SumOfNumberOfWheelsResponse) client.sendAndReceiveCommand(request);

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Сумма значений поля numberOfWheels для всех элементов коллекции: " + response.sum);
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}
