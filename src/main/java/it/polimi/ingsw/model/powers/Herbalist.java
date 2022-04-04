package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;

public class Herbalist extends PowerCard {

    private int noEntryCards;

    public Herbalist(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.HERBALIST);
        setCost(2);
    }

    @Override
    public void power() {
        super.power();

        //TODO

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

    public void addEntryCard() {

        //TODO

    }

}
