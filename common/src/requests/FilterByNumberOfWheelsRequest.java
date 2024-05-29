package requests;

import utility.Commands;

public class FilterByNumberOfWheelsRequest extends Request {
    public final int numberOfWheels;

    public FilterByNumberOfWheelsRequest(int numberOfWheels) {
        super(Commands.FILTER_BY_NUMBER_OF_WHEELS);
        this.numberOfWheels = numberOfWheels;
    }
}
