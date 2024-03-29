package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * EFFECT: Choose an Island and resolve the Island as if Mother Nature had ended her movement there.
 * Mother Nature will still move and the Island where she ends her movement will also be resolved.
 *
 * @author Christian Confalonieri
 */
public class Herald extends PowerCard {

    /**
     * In the constructor the character type and its usage cost is set.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Herald(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.HERALD);
        setCost(3);
    }

    public Herald() {
        super();
    }

    /**
     * Invokes the conquerIsland method on the chosen island
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();
        getGameHandler().getGame().conquerIsland(getGameHandler().getGame().getIslandFromId(getGameHandler().getGame().getEffectHandler().getChosenIslandUuid()));

        Island currentIsland = getGameHandler().getGame().getIslandFromId(getGameHandler().getGame().getEffectHandler().getChosenIslandUuid());

        if(currentIsland.checkUnifyNext()) {
            getGameHandler().getGame().unifyIsland(currentIsland,currentIsland.getNextIsland());
        }
        if(currentIsland.checkUnifyPrev()) {
            getGameHandler().getGame().unifyIsland(currentIsland,currentIsland.getPrevIsland());
        }

    }

}
