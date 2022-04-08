package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.entity.Student;

import java.util.List;

/**
 * @author Christian Confalonieri
 */
public class Friar extends PowerCard {

    private List<Student> students;

    /**
     * @author Christian Confalonieri
     */
    public Friar(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.FRIAR);
        setCost(1);

        for(int i=0; i<4; i++) {
            try {
                students.add(getGameHandler().getGame().getBag().pickStudent());
            } catch (EmptyBagException e) {  }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        students.remove(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(0));
        getGameHandler().getGame().getEffectHandler().getChosenIsland().addStudent(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(0));
        try {
            students.add(getGameHandler().getGame().getBag().pickStudent());
        } catch (EmptyBagException e) { }
    }

}
