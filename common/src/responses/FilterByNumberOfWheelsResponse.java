package responses;

import models.Vehicle;
import utility.Commands;

import java.util.List;

public class FilterByNumberOfWheelsResponse extends Response {
    public final List<Vehicle> vehicles;

    public FilterByNumberOfWheelsResponse(List<Vehicle> vehicles, String error) {
        super(Commands.FILTER_BY_NUMBER_OF_WHEELS, error);
        this.vehicles = vehicles;
    }
}
