package it.polimi.ingsw.model;

import java.util.List;
import java.util.Map;

public class School implements PawnHandler{

    private Map<PawnColor, List<Student>> diningRoom;
    private List<Student> entrance;
    private List<Professor> professorTable;
    private List<Tower> towers;


    @Override
    public void addPawn(Pawn pawn) {

    }

    @Override
    public void movePawnTo(PawnHandler destination, Pawn pawn) {

    }

    public int entranceToDiningRoom(Student student) {

    }

    public void moveTower(Island island) {

    }

    public int getStudentsNumber(PawnColor color) {

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
}
