package commands;

import network.UDPClient;
import utility.Console;
import exceptions.*;
import requests.*;
import responses.*;

import java.io.IOException;

/**
 * Команда 'print_field_ascending_fuel_type'. Выводит значения поля fuelType всех элементов в порядке возрастания.
 */
public class PrintFieldAscendingFuelType extends Command {
    private final Console console;
    private final UDPClient client;

    public PrintFieldAscendingFuelType(Console console, UDPClient client) {
        super("print_field_ascending_fuel_type");
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
            var request = new PrintFieldAscendingFuelTypeRequest();
            var response = (PrintFieldAscendingFuelTypeResponse) client.sendAndReceiveCommand(request);

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Значения поля fuelType всех элементов в порядке возрастания:");
            response.fuelTypes.forEach(fuelType -> console.println(fuelType.toString()));
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
