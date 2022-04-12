package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.TurnPhase;
import it.polimi.ingsw.model.power.PowerCard;

public class GameHandler4P extends GameHandler{

    protected GameHandler4P(Game game) {
        super(game);
    }

    public static GameHandler gameHandlerBuilder4P(Game game) {
        GameHandler gameHandler = new GameHandler4P(game);
        game.powerCards = PowerCard.getThreeUniquePowerCards(gameHandler);
        return gameHandler;
    }

    /**
     * Check if the game is in an end situation and update the gameState if so
     *
     * @return true if the game has ended
     */
    @Override
    public boolean checkEndGame() {
        Game4P game4p = (Game4P) game;
        return  // One team has finished his towers
                game4p.getTeams().stream().map(t -> t.getTeamTowers().size()).anyMatch(size -> size == 0) ||
                        // There are only 3 islands
                        game4p.islands.size() <= 3 ||
                        // The cards or the students finished and all the players finished their turns
                        (game4p.players.stream().map(p -> p.getHandCards().size()).anyMatch(size -> size == 0) ) &&
                                firstTurnPlayer == getNextPlayer()
                        // The students in the bag finished and all the players finished their turns
                        || game4p.bag.isEmpty() && gamePhase == GamePhase.TURN && turnPhase == TurnPhase.MOVEFROMCLOUD &&
                                firstTurnPlayer == getNextPlayer()
                ;
    }
}
