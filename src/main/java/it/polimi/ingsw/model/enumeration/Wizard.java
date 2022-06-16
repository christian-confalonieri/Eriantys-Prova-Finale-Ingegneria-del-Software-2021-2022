package it.polimi.ingsw.model.enumeration;

import it.polimi.ingsw.exceptions.InvalidColor;

public enum Wizard {

    BLUE, PURPLE, YELLOW, GREEN;

    /**
     * Converts a color from string to Wizard.
     * @param color The color string to be converted
     * @return Wizard
     * @throws InvalidColor
     * @author Christian Confalonieri
     */
    public static Wizard parseColor(String color) throws InvalidColor {
        return switch (color.toUpperCase()) {
            case "BLUE", "B" -> BLUE;
            case "PURPLE", "P" -> PURPLE;
            case "YELLOW", "Y" -> YELLOW;
            case "GREEN", "G" -> GREEN;
            default -> throw new InvalidColor();
        };
    }

}
