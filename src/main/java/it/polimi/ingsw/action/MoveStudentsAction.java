package it.polimi.ingsw.action;

import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @author Christian Confalonieri
 */
public class MoveStudentsAction extends PlayAction{
    private final List<Student> toDiningRoom;
    private final Map<Student, Island>  toIsland;
    
    public MoveStudentsAction(String playerId, List<Student> toDiningRoom, Map<Student, Island> toIsland) {
        super(ActionType.MOVESTUDENTS, playerId);
        this.toDiningRoom = toDiningRoom;
        this.toIsland = toIsland;
    }

    public List<Student> getToDiningRoom() {
        return toDiningRoom;
    }

    public Map<Student, Island> getToIsland() {
        return toIsland;
    }
}
