package it.polimi.ingsw.model;

import java.util.Map;

public class Farmer extends PowerCard{

    private Map<Player, Professor> movedProfessors;

    public Farmer(GameHandler gameHandler) {
        super(gameHandler);
    }

    @Override
    public void power() {
        super.power();
    }

    @Override
    public void endPower() {
        super.endPower();
    }

}
