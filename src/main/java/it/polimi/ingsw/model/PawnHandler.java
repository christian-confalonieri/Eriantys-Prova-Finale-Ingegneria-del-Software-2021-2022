package it.polimi.ingsw.model;

public interface PawnHandler {

    /**
     * The received parameter is moved to the platform in which the method is called.
     *
     * @param pawn the pawn to add to the object
     */
    void addPawn(Pawn pawn);

    /**
     * The pawn is moved to the destination specified
     *
     * @param destination the destination of the pawn
     * @param pawn the pawn to move
     */
    void movePawnTo(PawnHandler destination, Pawn pawn);

}
