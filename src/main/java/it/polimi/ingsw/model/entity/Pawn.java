package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.exceptions.InvalidColor;
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

    /**
     * Converts a color from string to PawnColor.
     * @param color The color string to be converted
     * @return PawnColor
     * @throws InvalidColor
     * @author Christian Confalonieri
     */
    public static PawnColor parseColor(String color) throws InvalidColor {
        PawnColor pawnColor = switch(color.toUpperCase()) {
          case "RED", "R" -> PawnColor.RED;
          case "YELLOW", "Y" -> PawnColor.YELLOW;
          case "GREEN", "G" -> PawnColor.GREEN;
          case "BLUE", "B" -> PawnColor.BLUE;
          case "PINK", "P" -> PawnColor.PINK;
          default -> throw new InvalidColor();
        };
        return pawnColor;
    }
}
