package responses;

import utility.Commands;

public class SumOfNumberOfWheelsResponse extends Response {
    public final int sum;

    public SumOfNumberOfWheelsResponse(int sum, String error) {
        super(Commands.SUM_OF_NUMBER_OF_WHEELS, error);
        this.sum = sum;
    }
}
