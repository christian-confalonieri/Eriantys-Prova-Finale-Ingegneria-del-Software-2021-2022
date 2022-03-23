package it.polimi.ingsw.model;

public interface PawnHandler {

    /**
     * The received parameter is moved to the platform in which the method is called.
     */
    void addPawn(Pawn pawn);

    /**
     * The second received parameter is moved to the destination specified as the first parameter.
     */
    void movePawnTo(PawnHandler destination, Pawn pawn);

}
