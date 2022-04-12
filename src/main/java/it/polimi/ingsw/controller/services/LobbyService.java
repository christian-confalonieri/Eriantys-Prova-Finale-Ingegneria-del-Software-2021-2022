package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.JoinGameAction;
import it.polimi.ingsw.action.NewGameAction;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.Server;

public class LobbyService {
    public static void newGame(NewGameAction action) throws InvalidAction {
        GameLobby lobby = new GameLobby(action.getGameRules(), action.getNumberOfPlayers());
        Server.getInstance().addNewGameLobby(lobby);
        joinGame(new JoinGameAction(action.getPlayerId(), lobby.getGameLobbyId(), action.getPlayerName(), action.getWizard()));
    }

    public static void joinGame(JoinGameAction action) throws InvalidAction {
        GameLobby lb = Server.getInstance().getGameLobby(action.getGameLobbyId());

        if(lb == null) throw new InvalidAction("JoinGame: Invalid lobby ID");

        boolean hasBeenAdded = lb.addPlayer(action.getPlayerId(), action.getPlayerName(), action.getWizard());
        if(!hasBeenAdded) throw new InvalidAction("JoinGame: Name or Wizard Already Taken");

        // TODO Communicate Join Ok

        if(lb.canStart()) {
            try {
                Server.getInstance().startGame(lb);
                // TODO Communicate gameStart
            } catch (InvalidNewGameException e) {
                // TODO Communicate failing to launch new game
                e.printStackTrace();
            }
        }
    }
}
