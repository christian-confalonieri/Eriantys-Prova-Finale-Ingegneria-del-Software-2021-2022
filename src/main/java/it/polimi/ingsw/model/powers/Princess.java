package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.Student;

import java.util.List;

/**
 * @author Christian Confalonieri
 */
public class Princess extends PowerCard {

    private List<Student> students;

    /**
     * @author Christian Confalonieri
     */
    public Princess(GameHandler gameHandler) throws EmptyBagException {
        super(gameHandler);
        setType(PowerType.PRINCESS);
        setCost(2);

        for(int i=0; i<4; i++) {
            students.add(getGameHandler().getGame().getBag().pickStudent());
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() throws EmptyBagException {
        super.power();

        students.remove(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(0));
        getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().addDiningRoom(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(0));
        students.add(getGameHandler().getGame().getBag().pickStudent());

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
