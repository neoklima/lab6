package responses;

import utility.Commands;

public class AddIfMaxResponse extends Response {
    public final boolean wasAdded;
    public final int newId;

    public AddIfMaxResponse(boolean wasAdded, int newId, String error) {
        super(Commands.ADD_IF_MAX, error);
        this.wasAdded = wasAdded;
        this.newId = newId;
    }
}
