package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

import java.util.ArrayList;
import java.util.List;

public class Thief extends PowerCard {

    /**
     * @author Christian Confalonieri
     */
    public Thief(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.THIEF);
        setCost(3);
    }

    /**
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
