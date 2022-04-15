package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.model.enumeration.PawnColor;

public abstract class Pawn extends Entity {

    private final PawnColor color;

    public PawnColor getColor() {
        return color;
    }

    public Pawn(PawnColor color) {
        super();
        this.color = color;
    }
}
