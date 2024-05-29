package commands;

import requests.Request;
import responses.*;
import repositories.VehicleRepository;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 * 
 */
public class ShowServer extends CommandServer {
    private final VehicleRepository productRepository;

    public ShowServer(VehicleRepository productRepository) {
        super("show", "вывести все элементы коллекции");
        this.productRepository = productRepository;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            return new ShowResponse(productRepository.sorted(), null);
        } catch (Exception e) {
            return new ShowResponse(null, e.toString());
        }
    }
}