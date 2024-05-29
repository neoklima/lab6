package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;
import models.Vehicle;

/**
 * Команда 'remove_greater'. Удаляет элементы из коллекции, которые больше заданного элемента.
 */
public class RemoveGreaterServer extends CommandServer {
    private final VehicleRepository vehicleRepository;

    public RemoveGreaterServer(VehicleRepository vehicleRepository) {
        super("remove_greater {element}", "удалить элементы из коллекции, которые больше заданного элемента");
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Response apply(Request request) {
        var req = (RemoveGreaterRequest) request;
        try {
            var referenceVehicle = req.vehicle;
            var initialSize = vehicleRepository.size();

            boolean removed = vehicleRepository.get().removeIf(vehicle -> vehicle.compareTo(referenceVehicle) > 0);
            var removedCount = initialSize - vehicleRepository.size();

            return new RemoveGreaterResponse(removedCount, null);
        } catch (Exception e) {
            return new RemoveGreaterResponse(0, e.toString());
        }
    }
}
