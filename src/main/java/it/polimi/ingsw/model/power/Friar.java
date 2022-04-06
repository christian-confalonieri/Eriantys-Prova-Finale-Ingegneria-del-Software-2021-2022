package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * In setup, draw 4 Students and place them on this card.
 * EFFECT: Take 1 Student from this card and place it on an Island of your choice. Then, draw a new Student from the Bag and place it on this card.
 *
 * @author Christian Confalonieri
 */
public class Friar extends PowerCard {

    private List<Student> students = new ArrayList<>();

    /**
     * In the constructor the character type and its usage cost is set.
     * 4 students are also added from the bag.
     * The students are copied in the chosenStudents1 list.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Friar(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.FRIAR);
        setCost(1);

        for(int i=0; i<4; i++) {
            try {
                students.add(getGameHandler().getGame().getBag().pickStudent());
            } catch (EmptyBagException e) { }
        }
    }

    /**
     * This method removes a student from this card, adds him to a chosen island,
     * and then draws a student from the bag again and adds him to the card.
     *
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

    public List<Student> getStudents() {
        return students;
    }
}
