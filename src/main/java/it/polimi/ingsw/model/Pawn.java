package it.polimi.ingsw.model;

public abstract class Pawn {

    private final PawnColor color;

    public PawnColor getColor() {
        return color;
    }

    public Pawn(PawnColor color) {
        this.color = color;
    }
}
