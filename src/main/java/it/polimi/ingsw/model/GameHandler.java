package it.polimi.ingsw.model;

import java.util.List;

public class GameHandler {

    protected Player currentPlayer;
    protected GamePhase gamePhase;
    protected TurnPhase turnPhase;
    protected Player firstTurnPlayer;
    protected boolean ended;
    protected Game game;

    /**
     * Advance the state of the game after all the actions in the current state has been performed
     */
    public void advance() {
        switch(gamePhase) {

            case PREPARATION:
                currentPlayer = getNextPlayer();
                if(currentPlayer == firstTurnPlayer) { // All players played a card
                    gamePhase = GamePhase.TURN;
                    turnPhase = TurnPhase.MOVESTUDENTS;
                    firstTurnPlayer = game.calculateFirstTurnPlayer();
                    currentPlayer = firstTurnPlayer;
                }
                else {
                    gamePhase = GamePhase.PREPARATION;
                }
                return;

            case TURN:
                switch (turnPhase) {
                    case MOVESTUDENTS:
                        turnPhase = TurnPhase.MOVEMOTHER;
                        return;
                    case MOVEMOTHER:
                        turnPhase = TurnPhase.MOVEFROMCLOUD;
                        return;
                    case MOVEFROMCLOUD:
                        currentPlayer = getNextPlayer();
                        if(currentPlayer == firstTurnPlayer) {
                            gamePhase = GamePhase.PREPARATION;
                        }
                }
        }
    }


    public Player getNextPlayer() {
        return game.players.get((game.players.indexOf(currentPlayer) + 1) % (game.players.size()));
    }

    public boolean isEnded() {
        return ended;
    }

    public Game getGame() {
        return game;
    }

    /**
     * Constructs a game handler, adding the state to the passed game object
     *
     * @param game the game to handle
     */
    public GameHandler(Game game) {
        this.game = game;
        firstTurnPlayer = game.players.get(0);
        gamePhase = GamePhase.PREPARATION;
        turnPhase = TurnPhase.MOVESTUDENTS;
        currentPlayer = firstTurnPlayer;
        ended = false;
    }

    /**
     * Check if the game is in an end situation
     * Must be called at the end of each player turn
     *
     * @return true if the game has ended
     */
    public boolean checkEndGame() {
        boolean finished =  // One player has finished his towers
                game.players.stream().map(p -> p.getSchool().getTowers().size()).anyMatch(size -> size == 0) ||
                        // There are only 3 islands
                        game.islands.size() <= 3 ||
                        // The cards or the students finished and all the players finished their turns
                        (game.players.stream().map(p -> p.getHandCards().size()).anyMatch(size -> size == 0) ) &&
                                firstTurnPlayer == getNextPlayer()
                        // The students in the bag finished and all the players finished their turns
                        || game.bag.isEmpty() && gamePhase == GamePhase.TURN && turnPhase == TurnPhase.MOVEFROMCLOUD &&
                                firstTurnPlayer == getNextPlayer()
                ;
        ended = finished;
        return finished;
    }
}
