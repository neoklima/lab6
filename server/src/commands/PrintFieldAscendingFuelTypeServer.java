package commands;

import requests.*;
import responses.*;
import repositories.VehicleRepository;
import models.FuelType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'print_field_ascending_fuel_type'. Выводит значения поля fuelType всех элементов в порядке возрастания.
 */
public class PrintFieldAscendingFuelTypeServer extends CommandServer {
    private final VehicleRepository vehicleRepository;

    public PrintFieldAscendingFuelTypeServer(VehicleRepository vehicleRepository) {
        super("print_field_ascending_fuel_type", "вывести значения поля fuelType всех элементов в порядке возрастания");
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Response apply(Request request) {
        try {
            List<FuelType> fuelTypes = vehicleRepository.get().stream()
                    .map(vehicle -> vehicle.getFuelType())
                    .sorted()
                    .collect(Collectors.toList());
            return new PrintFieldAscendingFuelTypeResponse(fuelTypes, null);
        } catch (Exception e) {
            return new PrintFieldAscendingFuelTypeResponse(null, e.toString());
        }
    }
}

