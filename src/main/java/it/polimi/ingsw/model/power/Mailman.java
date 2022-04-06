package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.MotherNature;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

public class Mailman extends PowerCard {

    /**
     * @author Christian Confalonieri
     */
    public Mailman(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.MAILMAN);
        setCost(1);
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        int n=getGameHandler().getGame().getEffectHandler().getAdditionalMoves();
        MotherNature motherNature = getGameHandler().getGame().getMotherNature();

        Island island = motherNature.isOn();
        Island landingIsland = island;
        for (int i = 0; i < n; i++) {
            landingIsland = landingIsland.getNextIsland();
        }
        island = landingIsland;

        motherNature.setIsland(island);

        getGameHandler().getGame().getEffectHandler().setAdditionalMoves(0);
    }

}
