package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.model.enumeration.TowerColor;

public class Tower extends Entity {

    private TowerColor color;
    private transient Player owner;

    public Tower(TowerColor color, Player owner) {
        super();
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
