package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.model.enumeration.PawnColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Airoldi, Christian Confalonieri, Alessandro Ettore
 */
public class School extends Entity {

    private Map<PawnColor, List<Student>> diningRoom;
    private List<Student> entrance;
    private List<Professor> professorTable;
    private List<Tower> towers;


    public School() {
        super();
        diningRoom = new HashMap<>();
        for (PawnColor pawnColor: PawnColor.values()) {
            diningRoom.put(pawnColor, new ArrayList<>());
        }
        professorTable = new ArrayList<>();
        entrance = new ArrayList<>();
        towers = new ArrayList<>();
    }

    public List<Student> getEntrance() { return this.entrance; }

    /**
     * Adds a student to the entrance of the school.
     *
     * @param student the student to be added
     * @author Christian Confalonieri
     */
    public void addEntrance(Student student) { entrance.add(student); }

    /**
     * Removes a student to the entrance of the school.
     *
     * @param student the student to be removed
     * @author Christian Confalonieri
     */
    public void removeEntrance(Student student) { entrance.remove(student); }

    /**
     * Adds a student to the dining room of the school.
     *
     * @param student the student to be added
     * @author Christian Confalonieri
     */
    public void addDiningRoom(Student student) { diningRoom.get(student.getColor()).add(student); }

    public Map<PawnColor, List<Student>> getDiningRoom () {
        return  this.diningRoom;
    }

    /**
     * Removes a student to the dining room of the school.
     *
     * @param student the student to be removed
     * @author Christian Confalonieri
     */
    public void removeDiningRoom(Student student) { diningRoom.get(student.getColor()).remove(student); }

    /**
     * returns students to the dining room.
     *
     * @param color the color of the dining room section
     * @author Christian Confalonieri
     */
    public List<Student> getStudentsDiningRoom(PawnColor color) { return diningRoom.get(color); }

    /**
     * Move a student from the entrance to the diningRoom.
     * Returns the coin if the student is added in a coin earning place
     *
     * @param student student to move
     * @return the number of coins earned (max 1 with the current rules)
     * @author Christian Confalonieri
     */
    public int entranceToDiningRoom(Student student) {

            entrance.remove(student);
            diningRoom.get(student.getColor()).add(student);
            if(diningRoom.get(student.getColor()).size() % 3 == 0) {
                return 1;
            }
            else {
                return 0;
            }

    }

    /**
     * Move a student from the entrance of the school to the chosen island
     *
     * @param student the student to move
     * @param island the island to put the student on
     */
    public void entranceToIsland(Student student, Island island) {
        this.entrance.remove(student);
        island.addStudent(student);
    }

    /**
     * This method returns the total number of students of a specific color that are sitting in the dining room.
     *
     * @param color the color of which we want to know the number of pawns
     * @return the total number of students of a specific color that are sitting in the dining room
     * @author Christian Confalonieri
     */
    public int getStudentsNumber(PawnColor color) {
        List<Student> value = diningRoom.get(color);
        if ( value == null ) {
            return 0;
        } else {
            return value.size();
        }
    }



    /**
     * Adds a professor to the school.
     *
     * @param professor the professor to be added
     * @author Christian Confalonieri
     */
    public void addProfessor(Professor professor) { professorTable.add(professor); }

    /**
     * Removes a professor to the school.
     *
     * @param professor the professor to be removed
     * @author Christian Confalonieri
     */
    public void removeProfessor(Professor professor) { professorTable.remove(professor); }


    public List<Professor> getProfessorTable() {
        return professorTable;
    }

    /**
     * this method returns true if there is a professor of the given color in the school.
     *
     * @param color the color of the professor to check
     * @return true if there is the professor
     */
    public boolean hasProfessor(PawnColor color) {
        return professorTable.stream().map(Pawn::getColor).anyMatch(profColor -> profColor.equals(color));
    }

    /**
     * this method returns the professor of the given color in the school.
     *
     * @param color the color of the professor to check
     * @return the professor
     */
    public Professor getProfessor(PawnColor color) {
        return professorTable.stream().filter(professor -> professor.getColor().equals(color)).findAny().get();
    }



    public List<Tower> getTowers() {
        return towers;
    }

    /**
     * This method moves the first tower available in the school to the island specified ad the parameter.
     *
     * @param island is where the tower has to be moved
     */
    public void moveTower(Island island) {
        // The first tower is removed from the list in this class and is placed on the island
        island.addTower(this.towers.get(0));
        this.towers.remove(0);
    }


    /**
     * Adds a tower to the school.
     *
     * @param tower the tower to be added
     */
    public void addTower(Tower tower) {
        towers.add(tower);
    }
}
