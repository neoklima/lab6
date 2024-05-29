package requests;

import models.Vehicle;
import utility.Commands;

public class AddRequest extends Request {
    public final Vehicle vehicle;

    public AddRequest(Vehicle vehicle) {
        super(Commands.ADD);
        this.vehicle = vehicle;
    }
}