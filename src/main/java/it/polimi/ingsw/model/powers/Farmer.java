package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Professor;

import java.util.Map;

public class Farmer extends PowerCard {

    private Map<Player, Professor> movedProfessors;

    public Farmer(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.FARMER);
        setCost(2);
    }

    @Override
    public void power() {
        super.power();

        //TODO
    }

    @Override
    public void endPower() {
        super.endPower();

        //TODO
    }

}
