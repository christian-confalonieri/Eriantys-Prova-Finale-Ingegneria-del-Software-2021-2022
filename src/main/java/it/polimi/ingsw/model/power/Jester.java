package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.entity.Student;

import java.util.List;

/**
 * @author Christian Confalonieri
 */
public class Jester extends PowerCard {

    private List<Student> students;

    /**
     * @author Christian Confalonieri
     */
    public Jester(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.JESTER);
        setCost(1);

        for(int i=0; i<6; i++) {
            try {
                students.add(getGameHandler().getGame().getBag().pickStudent());
            } catch (EmptyBagException e) { }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        int size = students.size();
        students.removeAll(getGameHandler().getGame().getEffectHandler().getChosenStudents1());
        students.addAll(getGameHandler().getGame().getEffectHandler().getChosenStudents2());
        for(int i=0; i< size; i++) {
            getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().removeEntrance(getGameHandler().getGame().getEffectHandler().getChosenStudents2().get(i));
            getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().addEntrance(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(i));
        }

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
