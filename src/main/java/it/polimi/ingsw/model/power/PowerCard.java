package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

import javax.swing.text.html.MinimalHTMLWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Christian Confalonieri
 */
public abstract class PowerCard {


    private int cost;
    private int baseCost;
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
        gameHandler.getGame().getEffectHandler().setEffectActive(true);
        gameHandler.getGame().getEffectHandler().setEffectPlayer(gameHandler.getCurrentPlayer());
        if(cost==baseCost) {
            cost++;
        }
    }

    /**
     * @author Christian Confalonieri
     */
    public void endPower() {
        gameHandler.getGame().getEffectHandler().setEffectActive(false);
        gameHandler.getGame().getEffectHandler().setEffectPlayer(null);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
        this.baseCost = cost;
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

    public static List<PowerCard> getThreeUniquePowerCards(GameHandler gameHandler) {
        List<PowerCard> powerCards = new ArrayList<>();
        Random cardGen = new Random();
        int i1 = cardGen.nextInt(PowerType.values().length);
        int i2;
        do {
            i2 = cardGen.nextInt(PowerType.values().length);
        } while(i2 == i1);
        int i3;
        do {
            i3 = cardGen.nextInt(PowerType.values().length);
        } while(i3 == i1 || i3 == i2);

        powerCards.add(createPowerCard(PowerType.values()[i1], gameHandler));
        powerCards.add(createPowerCard(PowerType.values()[i2], gameHandler));
        powerCards.add(createPowerCard(PowerType.values()[i3], gameHandler));

        return powerCards;
    }

    public static PowerCard createPowerCard(PowerType type, GameHandler gameHandler) {
        return switch (type) {
            case FRIAR -> new Friar(gameHandler);
            case FARMER -> new Farmer(gameHandler);
            case HERALD -> new Herald(gameHandler);
            case MAILMAN -> new Mailman(gameHandler);
            case HERBALIST -> new Herbalist(gameHandler);
            case CENTAUR -> new Centaur(gameHandler);
            case JESTER -> new Jester(gameHandler);
            case KNIGHT -> new Knight(gameHandler);
            case HARVESTER -> new Harvester(gameHandler);
            case MINSTREL -> new Minstrel(gameHandler);
            case PRINCESS -> new Princess(gameHandler);
            case THIEF -> new Thief(gameHandler);
        };
    }
}
