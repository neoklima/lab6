package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;
import models.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'filter_by_number_of_wheels'. Выводит элементы, значение поля numberOfWheels которых равно заданному.
 */
public class FilterByNumberOfWheelsServer extends CommandServer {
    private final VehicleRepository vehicleRepository;

    public FilterByNumberOfWheelsServer(VehicleRepository vehicleRepository) {
        super("filter_by_number_of_wheels {numberOfWheels}", "вывести элементы, значение поля numberOfWheels которых равно заданному");
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Response apply(Request request) {
        try {
            var req = (FilterByNumberOfWheelsRequest) request;
            List<Vehicle> filteredVehicles = vehicleRepository.get().stream()
                    .filter(vehicle -> vehicle.getNumberOfWheels() == req.numberOfWheels)
                    .collect(Collectors.toList());
            return new FilterByNumberOfWheelsResponse(filteredVehicles, null);
        } catch (Exception e) {
            return new FilterByNumberOfWheelsResponse(null, e.toString());
        }
    }
}

