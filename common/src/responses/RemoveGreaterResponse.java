package responses;

import utility.Commands;

public class RemoveGreaterResponse extends Response {
    public final int removedCount;

    public RemoveGreaterResponse(int removedCount, String error) {
        super(Commands.REMOVE_LOWER, error);
        this.removedCount = removedCount;
    }
}