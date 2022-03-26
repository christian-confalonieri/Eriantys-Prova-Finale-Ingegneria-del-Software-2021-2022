package it.polimi.ingsw.model;

public interface PawnHandler {

    /**
     * The received parameter is moved to the platform in which the method is called.
     *
     * @param pawn the pawn to add to the object
     */
    void addPawn(Pawn pawn);

    /**
     * The pawn (2nd parameter) is moved to the destination specified as the 1st parameter.
     *
     * @param destination the destination of the pawn
     * @param pawn the pawn to be moved
     */
    void movePawnTo(PawnHandler destination, Pawn pawn);

}
