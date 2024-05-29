package requests;

import models.Vehicle;
import utility.Commands;

public class AddIfMaxRequest extends Request {
    public final Vehicle vehicle;

    public AddIfMaxRequest(Vehicle vehicle) {
        super(Commands.ADD_IF_MAX);
        this.vehicle = vehicle;
    }
}
