package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 * 
 */
public class RemoveByIdServer extends CommandServer {
    private final VehicleRepository productRepository;

    public RemoveByIdServer(VehicleRepository productRepository) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.productRepository = productRepository;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (RemoveByIdRequest) request;

        try {
            if (!productRepository.checkExist(req.id)) {
                return new RemoveByIdResponse("Продукта с таким ID в коллекции нет!");
            }

            productRepository.remove(req.id);
            return new RemoveByIdResponse(null);
        } catch (Exception e) {
            return new RemoveByIdResponse(e.toString());
        }
    }
}