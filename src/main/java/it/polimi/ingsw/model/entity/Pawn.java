package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.model.enumeration.PawnColor;

public abstract class Pawn {

    private final PawnColor color;

    public PawnColor getColor() {
        return color;
    }

    public Pawn(PawnColor color) {
        this.color = color;
    }
}
