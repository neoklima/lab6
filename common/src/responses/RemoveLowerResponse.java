package responses;

import utility.Commands;

public class RemoveLowerResponse extends Response {
    public final int removedCount;

    public RemoveLowerResponse(int removedCount, String error) {
        super(Commands.REMOVE_LOWER, error);
        this.removedCount = removedCount;
    }
}