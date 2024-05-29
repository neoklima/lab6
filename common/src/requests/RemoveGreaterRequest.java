package requests;

import models.Vehicle;
import utility.Commands;

public class RemoveGreaterRequest extends Request {
    public final Vehicle vehicle;

    public RemoveGreaterRequest(Vehicle vehicle) {
        super(Commands.REMOVE_LOWER);
        this.vehicle = vehicle;
    }
}