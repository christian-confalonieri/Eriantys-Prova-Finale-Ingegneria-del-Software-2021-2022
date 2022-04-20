package it.polimi.ingsw.action;

public class ACK extends Action {
    private final ActionType ackAction;
    private final boolean ok;
    private final String message;

    public ACK(String playerId, ActionType ackAction, String message, boolean ok) {
        super(ActionType.ACK, playerId);
        this.ackAction = ackAction;
        this.message = message;
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        return ok;
    }

    public ActionType isAckOfAction() {
        return ackAction;
    }
}
