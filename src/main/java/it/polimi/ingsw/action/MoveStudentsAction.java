package it.polimi.ingsw.action;

import it.polimi.ingsw.model.entity.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christian Confalonieri
 */
public class MoveStudentsAction extends PlayAction{
    private final List<Student> toDiningRoom;
    private final List<Student> studentsToIsland = new ArrayList<>();
    private final List<String> islandsUUID = new ArrayList<>();
    
    public MoveStudentsAction(String playerId, List<Student> toDiningRoom, Map<Student,String> toIslands) {
        super(ActionType.MOVESTUDENTS, playerId);
        this.toDiningRoom = toDiningRoom;
        for(Student student : toIslands.keySet()) {
            studentsToIsland.add(student);
            islandsUUID.add(toIslands.get(student));
        }
    }

    public List<Student> getToDiningRoom() {
        return toDiningRoom;
    }

    /**
     * Returns a map by merging the information from the list studentsToIsland and IslandsUUID
     * @return The map containing students and their destinations (islands)
     * @author Christian Confalonieri
     */
    public Map<Student, String> getToIsland() {
        Map<Student,String> toIsland = new HashMap<>();
        for(int i=0; i<studentsToIsland.size(); i++) {
            toIsland.put(studentsToIsland.get(i),islandsUUID.get(i));
        }
        return toIsland;
    }
}
