package it.polimi.ingsw.action;

import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Student;

import java.util.List;
import java.util.Map;

public class MoveStudentsAction extends PlayAction{
    private List<Student> toDiningRoom;
    private Map<Student, Island>  toIsland;
    
    public MoveStudentsAction(ActionType actionType, String playerId, List<Student> toDiningRoom, Map<Student, Island> toIsland) {
        super(actionType, playerId);
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
