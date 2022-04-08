package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * In Setup, draw 6 Students and place them on this card.
 * EFFECT: You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance.
 *
 * @author Christian Confalonieri
 */
public class Jester extends PowerCard {

    private List<Student> students = new ArrayList<>();

    /**
     * In the constructor the character type and its usage cost is set.
     * 6 students are also added from the bag.
     * The students are copied in the chosenStudents1 list.
     *
     * @param gameHandler
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
     * This method exchanges up to 3 students on this card for equal numbers of students who are at the entrance of their school.
     * The students to be moved belonging to the card are copied in the chosenStudents1 list, while those of the entrance in the chosenStudents2 list.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        int size = getGameHandler().getGame().getEffectHandler().getChosenStudents1().size();
        students.removeAll(getGameHandler().getGame().getEffectHandler().getChosenStudents1());
        students.addAll(getGameHandler().getGame().getEffectHandler().getChosenStudents2());
        for(int i=0; i < size; i++) {
            getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().removeEntrance(getGameHandler().getGame().getEffectHandler().getChosenStudents2().get(i));
            getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().addEntrance(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(i));
        }
    }

    public List<Student> getStudents() {
        return students;
    }
}
