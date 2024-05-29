package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 * 
 */
public class AddServer extends CommandServer {
    private final VehicleRepository productRepository;

    public AddServer(VehicleRepository productRepository) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.productRepository = productRepository;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (AddRequest) request;
        try {
            if (!req.vehicle.validate()) {
                return new AddResponse(-1, "Поля продукта не валидны! Продукт не добавлен!");
            }
            var newId = productRepository.add(req.vehicle);
            return new AddResponse(newId, null);
        } catch (Exception e) {
            return new AddResponse(-1, e.toString());
        }
    }
}