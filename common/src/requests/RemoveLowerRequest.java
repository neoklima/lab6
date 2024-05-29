package requests;

import models.Vehicle;
import utility.Commands;

public class RemoveLowerRequest extends Request {
    public final Vehicle vehicle;

    public RemoveLowerRequest(Vehicle vehicle) {
        super(Commands.REMOVE_LOWER);
        this.vehicle = vehicle;
    }
}