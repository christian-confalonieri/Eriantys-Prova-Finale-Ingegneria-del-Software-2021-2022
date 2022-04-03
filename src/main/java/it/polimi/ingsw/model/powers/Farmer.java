package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Professor;

import java.util.Map;

public class Farmer extends PowerCard {

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
