package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.server.PlayerLobby;

import java.util.List;

public class GameCreator {
    /**
     * Creates a new game given the rules and the players' names and wizards.
     * @param playersData The list of players in a lobby with the info on their name and wizard
     * @param gameRules The rules of the game
     * @return A new gameHandler
     * @throws InvalidNewGameException if the player are not 2, 3, 4 or the game creation failed.
     */
    public static GameHandler createGame(List<PlayerLobby> playersData, GameRules gameRules) throws InvalidNewGameException {
        switch (playersData.size()) {
            case 2:
                return GameHandler.gameHandlerBuilder(new Game(playersData, gameRules));
            case 3:
                return GameHandler.gameHandlerBuilder(new Game(playersData, gameRules));
            case 4:
                return GameHandler4P.gameHandlerBuilder4P(new Game4P(playersData, gameRules));
        }
        throw new InvalidNewGameException("Number of players not supported");
    }
}
