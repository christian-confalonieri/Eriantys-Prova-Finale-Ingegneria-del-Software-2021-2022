package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * EFFECT: Choose a type of Student: every player (including yourself)
 * must return 3 Students of that type from their Dining Room to the bag.
 * If any player has fewer than 3 Students of that type, return as many Students as they have.
 *
 * @author Christian Confalonieri
 */
public class Thief extends PowerCard {

    /**
     * In the constructor the character type and its usage cost is set.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Thief(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.THIEF);
        setCost(3);
    }

    public Thief() {
        super();
    }

    /**
     * This method moves 3 students of a chosen color from each player's dining room to the bag.
     * In case there are not enough students to move all of them are sent.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        List<Player> players = getGameHandler().getOrderedTurnPlayers();
        PawnColor color = getGameHandler().getGame().getEffectHandler().getThiefColor();
        List<Student> studentsBag = new ArrayList<>();
        List<Student> studentsDiningRoom;

        for(Player player : players) {
            studentsBag.clear();
            studentsDiningRoom = player.getSchool().getStudentsDiningRoom(color);
            for(int i=0;i<3;i++) {
                if(!studentsDiningRoom.isEmpty()) {
                    studentsBag.add(studentsDiningRoom.get(0));
                    studentsDiningRoom.remove(0);
                }

            }
            getGameHandler().getGame().getBag().addAllStudents(studentsBag);
        }
    }

}
