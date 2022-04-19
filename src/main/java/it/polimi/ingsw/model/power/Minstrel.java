package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * EFFECT: You may exchange up to 2 Students between your Entrance and your Dining Room.
 *
 * @author Christian Confalonieri
 */
public class Minstrel extends PowerCard {

    /**
     * In the constructor the character type and its usage cost is set.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Minstrel(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.MINSTREL);
        setCost(1);
    }

    public Minstrel() {
        super();
    }

    /**
     * This method simply exchange up to 2 Students between your Entrance and your Dining Room.
     * The students to be moved belonging to the entrance are copied in the chosenStudents1 list, while those of the dining room in the chosenStudents2 list.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        int size = getGameHandler().getGame().getEffectHandler().getChosenStudents1().size();
        for(int i=0; i< size; i++) {
            getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().removeEntrance(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(i));
            getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().removeDiningRoom(getGameHandler().getGame().getEffectHandler().getChosenStudents2().get(i));

            getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().addEntrance(getGameHandler().getGame().getEffectHandler().getChosenStudents2().get(i));
            getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().addDiningRoom(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(i));
        }

        getGameHandler().getGame().professorRelocate();
    }

}
