package responses;

import models.FuelType;
import utility.Commands;

import java.util.List;

public class PrintFieldAscendingFuelTypeResponse extends Response {
    public final List<FuelType> fuelTypes;

    public PrintFieldAscendingFuelTypeResponse(List<FuelType> fuelTypes, String error) {
        super(Commands.PRINT_FIELD_ASCENDING_FUEL_TYPE, error);
        this.fuelTypes = fuelTypes;
    }
}
