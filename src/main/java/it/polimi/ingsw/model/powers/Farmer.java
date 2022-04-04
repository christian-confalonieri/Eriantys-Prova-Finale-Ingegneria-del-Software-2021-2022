package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Professor;

import java.util.Map;

public class Farmer extends PowerCard {

    private Map<Player, Professor> movedProfessors;

    /**
     * @author Christian Confalonieri
     */
    public Farmer(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.FARMER);
        setCost(2);
    }

    @Override
    public void power() throws EmptyBagException {
        super.power();

        //TODO
    }

    @Override
    public void endPower() {
        super.endPower();

        //TODO
    }

}
