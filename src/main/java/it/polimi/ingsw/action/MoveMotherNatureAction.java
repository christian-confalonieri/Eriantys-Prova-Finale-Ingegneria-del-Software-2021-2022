package it.polimi.ingsw.action;

/**
 * @author Christian Confalonieri
 */
public class MoveMotherNatureAction extends PlayAction{
    private final int steps;

    public MoveMotherNatureAction(String playerId, int steps) {
        super(ActionType.MOVEMOTHERNATURE, playerId);
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }
}
