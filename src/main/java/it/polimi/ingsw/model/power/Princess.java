package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * In Setup, draw 4 Students and place them on this card.
 * EFFECT: Take 1 Student from this card and place it in your Dining Room.
 * Then, draw a new Student from the Bag and place it on this card.
 *
 * @author Christian Confalonieri
 */
public class Princess extends PowerCard {

    private List<Student> students = new ArrayList<>();

    /**
     * In the constructor the character type and its usage cost is set.
     * 4 students are also added from the bag.
     * The students are copied in the chosenStudents1 list.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Princess(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.PRINCESS);
        setCost(2);

        for(int i=0; i<4; i++) {
            try {
                students.add(getGameHandler().getGame().getBag().pickStudent());
            } catch (EmptyBagException e) {}
        }
    }

    /**
     * This method moves a chosen student from this card to their dining room,
     * then draws a new student from the bag and places them on the card.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        students.remove(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(0));
        getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().addDiningRoom(getGameHandler().getGame().getEffectHandler().getChosenStudents1().get(0));
        try {
            students.add(getGameHandler().getGame().getBag().pickStudent());
        } catch (EmptyBagException e) {}
    }

    public List<Student> getStudents() {
        return students;
    }
}
