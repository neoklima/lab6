package commands;

import requests.Request;
import responses.ClearResponse;
import responses.Response;
import repositories.VehicleRepository;

/**
 * Команда 'clear'. Очищает коллекцию.
 * 
 */
public class ClearServer extends CommandServer {
    private final VehicleRepository productRepository;

    public ClearServer(VehicleRepository productRepository) {
        super("clear", "очистить коллекцию");
        this.productRepository = productRepository;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            productRepository.clear();
            return new ClearResponse(null);
        } catch (Exception e) {
            return new ClearResponse(e.toString());
        }
    }
}