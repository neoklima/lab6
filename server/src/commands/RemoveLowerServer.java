package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;

/**
 * Команда 'remove_lower'. Удаляет элементы из коллекции, которые меньше заданного элемента.
 */
public class RemoveLowerServer extends CommandServer {
    private final VehicleRepository productRepository;

    public RemoveLowerServer(VehicleRepository productRepository) {
        super("remove_lower {element}", "удалить элементы из коллекции, которые меньше заданного элемента");
        this.productRepository = productRepository;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            var req = (RemoveLowerRequest) request;
            var referenceProduct = req.vehicle;
            var initialSize = productRepository.size();

            // Removing elements lower than the reference product
            boolean removed = productRepository.get().removeIf(product -> product.compareTo(referenceProduct) < 0);
            var removedCount = initialSize - productRepository.size();

            return new RemoveLowerResponse(removedCount, null);
        } catch (Exception e) {
            return new RemoveLowerResponse(0, e.toString());
        }
    }
}
