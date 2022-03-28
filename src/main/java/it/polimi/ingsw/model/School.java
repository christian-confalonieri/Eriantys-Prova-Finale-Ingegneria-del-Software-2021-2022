package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class School implements PawnHandler{

    private Map<PawnColor, List<Student>> diningRoom;
    private List<Student> entrance;
    private List<Professor> professorTable;
    private List<Tower> towers = new ArrayList<>();

    @Override
    public void addPawn(Pawn pawn) {
        // The pawn received as input is added to the students at the entrance.
        this.entrance.add((Student) pawn);
    }

    @Override
    public void movePawnTo(PawnHandler destination, Pawn pawn) {

    }

    public int entranceToDiningRoom(Student student) {

        return 0;
    }

    /**
     * This method moves the first tower available in the school to the island specified ad the parameter.
     *
     * @param island is where the tower has to be moved
     */
    public void moveTower(@NotNull Island island) {
        // The first tower is removed from the list in this class and is placed on the island
        island.addTower(this.towers.get(0));
        this.towers.remove(0);
    }

    /**
     * This method returns the total number of students of a specific color that are sitting in the dining room.
     *
     * @param color the color of which we want to know the number of pawns
     */
    public int getStudentsNumber(PawnColor color) {
        List<Student> value = diningRoom.get(color);

        if ( value == null ) {
            return 0;
        } else {
            return value.size();
        }
    }

    public List<Professor> getProfessorTable() {
        return professorTable;
    }

    public boolean hasProfessor(PawnColor color) {
        return professorTable.stream().map(Pawn::getColor).anyMatch(profColor -> profColor.equals(color));
    }

    public Professor getProfessor(PawnColor color) {
        return professorTable.stream().filter(professor -> professor.getColor().equals(color)).findAny().get();
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void addTower(Tower tower) {
        towers.add(tower);
    }
}
