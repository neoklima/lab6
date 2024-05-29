package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 * 
 */
public class UpdateServer extends CommandServer {
    private final VehicleRepository productRepository;

    public UpdateServer(VehicleRepository productRepository) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.productRepository = productRepository;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (UpdateRequest) request;
        try {
            if (!productRepository.checkExist(req.id)) {
                return new UpdateResponse("Продукта с таким ID в коллекции нет!");
            }
            if (!req.updatedVehicle.validate()) {
                return new UpdateResponse( "Поля продукта не валидны! Продукт не обновлен!");
            }

            productRepository.getById(req.id).update(req.updatedVehicle);
            return new UpdateResponse(null);
        } catch (Exception e) {
            return new UpdateResponse(e.toString());
        }
    }
}