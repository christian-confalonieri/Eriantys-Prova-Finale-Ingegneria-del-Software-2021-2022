package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * @author Christian Confalonieri
 */
public abstract class PowerCard {

    private int cost;
    private PowerType type;
    private GameHandler gameHandler;

    public PowerCard(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    /**
     * This method increases the cost of the card
     *
     * @author Christian Confalonieri
     */
    public void power() {
        gameHandler.getGame().getEffectHandler().setEffectPlayer(gameHandler.getCurrentPlayer());
        cost++;
    }

    /**
     * @author Christian Confalonieri
     */
    public void endPower() {
        gameHandler.getGame().getEffectHandler().setEffectPlayer(null);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public PowerType getType() {
        return type;
    }

    public void setType(PowerType type) {
        this.type = type;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}
