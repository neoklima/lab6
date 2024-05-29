package commands;

import network.UDPClient;
import utility.Console;
import exceptions.*;
import requests.*;
import responses.*;

import java.io.IOException;

/**
 * Команда 'filter_by_number_of_wheels numberOfWheels'. Выводит элементы, значение поля numberOfWheels которых равно заданному.
 */
public class FilterByNumberOfWheels extends Command {
    private final Console console;
    private final UDPClient client;

    public FilterByNumberOfWheels(Console console, UDPClient client) {
        super("filter_by_number_of_wheels {numberOfWheels}");
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
            if (arguments.length != 2) throw new WrongAmountOfElementsException();
            int numberOfWheels;
            try {
                numberOfWheels = Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                System.out.println("Неправильный формат числа");
                return false;
            }
            var request = new FilterByNumberOfWheelsRequest(numberOfWheels);
            var response = (FilterByNumberOfWheelsResponse) client.sendAndReceiveCommand(request);

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Элементы, значение поля numberOfWheels которых равно " + numberOfWheels + ":");
            response.vehicles.forEach(vehicle -> console.println(vehicle.toString()));
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
