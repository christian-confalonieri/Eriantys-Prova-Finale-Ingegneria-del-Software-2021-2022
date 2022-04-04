package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.model.enumeration.TowerColor;

public class Tower {

    private TowerColor color;
    private Player owner;

    public Tower(TowerColor color, Player owner) {
        this.color = color;
        this.owner = owner;
    }

    public TowerColor getColor() {
        return color;
    }

    public Player getOwner() {
        return owner;
    }
}
