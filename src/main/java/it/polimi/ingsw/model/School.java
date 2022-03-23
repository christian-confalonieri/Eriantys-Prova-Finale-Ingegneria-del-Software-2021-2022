package it.polimi.ingsw.model;

import java.util.List;
import java.util.Map;

public class School implements PawnHandler{

    private Map<PawnColor, List<Student>> diningRoom;
    private List<Student> entrance;
    private Professor professorTable;
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

    public void getStudentsNumber(PawnColor color) {

    }

}
