package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;
import models.Vehicle;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 */
public class AddIfMaxServer extends CommandServer {
    private final VehicleRepository vehicleRepository;

    public AddIfMaxServer(VehicleRepository vehicleRepository) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Response apply(Request request) {
        var req = (AddIfMaxRequest) request;
        try {
            if (!req.vehicle.validate()) {
                return new AddIfMaxResponse(false, -1, "Поля продукта не валидны! Продукт не добавлен!");
            }
            Vehicle maxVehicle = vehicleRepository.get().stream().max(Vehicle::compareTo).orElse(null);
            if (maxVehicle == null || req.vehicle.compareTo(maxVehicle) > 0) {
                int newId = vehicleRepository.add(req.vehicle);
                return new AddIfMaxResponse(true, newId, null);
            } else {
                return new AddIfMaxResponse(false, -1, null);
            }
        } catch (Exception e) {
            return new AddIfMaxResponse(false, -1, e.toString());
        }
    }
}

