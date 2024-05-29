package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;

/**
 * Команда 'sum_of_number_of_wheels'. Выводит сумму значений поля numberOfWheels для всех элементов коллекции.
 */
public class SumOfNumberOfWheelsServer extends CommandServer {
    private final VehicleRepository vehicleRepository;

    public SumOfNumberOfWheelsServer(VehicleRepository vehicleRepository) {
        super("sum_of_number_of_wheels", "вывести сумму значений поля numberOfWheels для всех элементов коллекции");
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Response apply(Request request) {
        try {
            int sum = vehicleRepository.get().stream().mapToInt(vehicle -> vehicle.getNumberOfWheels()).sum();
            return new SumOfNumberOfWheelsResponse(sum, null);
        } catch (Exception e) {
            return new SumOfNumberOfWheelsResponse(0, e.toString());
        }
    }
}
