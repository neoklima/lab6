package requests;

import models.Vehicle;
import utility.Commands;

public class UpdateRequest extends Request {
    public final int id;
    public final Vehicle updatedVehicle;

    public UpdateRequest(int id, Vehicle updatedVehicle) {
        super(Commands.UPDATE);
        this.id = id;
        this.updatedVehicle = updatedVehicle;
    }
}