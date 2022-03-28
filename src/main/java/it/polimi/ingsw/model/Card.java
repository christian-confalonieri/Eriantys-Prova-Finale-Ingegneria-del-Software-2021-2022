package it.polimi.ingsw.model;

public enum Card {

    ONE(1, 1),
    TWO(2, 1),
    THREE(3, 2),
    FOUR(4, 2),
    FIVE(5, 3),
    SIX(6, 3),
    SEVEN(7, 4),
    EIGHT(8, 4),
    NINE(9, 5),
    TEN(10, 5);

    private final int number;
    private final int movements;

    Card(int number, int movements) {
        this.number = number;
        this.movements = movements;
    }

    public int getNumber() {
        return number;
    }

    public int getMovements() {
        return movements;
    }
}
