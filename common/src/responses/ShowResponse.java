package responses;

import models.Vehicle;
import utility.Commands;

import java.util.List;

public class ShowResponse extends Response {
    public final List<Vehicle> vehicles;

    public ShowResponse(List<Vehicle> vehicles, String error) {
        super(Commands.SHOW, error);
        this.vehicles = vehicles;
    }
}
