package it.polimi.ingsw.action;

/**
 * @author Christian Confalonieri
 */
public class MoveMotherNatureAction extends PlayAction{
    int steps;
    public MoveMotherNatureAction(ActionType actionType, String playerId, int steps) {
        super(actionType, playerId);
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }
}
