package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * @author Christian Confalonieri
 */
public class Minstrel extends PowerCard {

    /**
     * @author Christian Confalonieri
     */
    public Minstrel(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.MINSTREL);
        setCost(1);
    }

    /**
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

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
