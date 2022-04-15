package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.JoinGameAction;
import it.polimi.ingsw.action.NewGameAction;
import it.polimi.ingsw.cli.ConsoleColor;
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
        System.out.println(ConsoleColor.CYAN + "New " + lobby.getLobbySize() + "P" + " gamelobby created [" + lobby.getGameLobbyId() + "]" + ConsoleColor.RESET);

        Server.getInstance().getClientNetHandler(action.getPlayerId()).send(lobby.getGameLobbyId()); // Replied with the lobby id
        joinGame(new JoinGameAction(action.getPlayerId(), lobby.getGameLobbyId(), action.getWizard()));
    }

    public static void joinGame(JoinGameAction action) throws InvalidAction {
        if(Server.getInstance().isWaitingInALobby(action.getPlayerId())) throw new InvalidAction("JoinGame : player already waiting in a lobby");

        GameLobby lb = Server.getInstance().getGameLobby(action.getGameLobbyId());

        if(lb == null) throw new InvalidAction("JoinGame: Invalid lobby ID");

        boolean hasBeenAdded = lb.addPlayer(action.getPlayerId(), action.getWizard());
        if(!hasBeenAdded) throw new InvalidAction("JoinGame: Name or Wizard Already Taken");

        System.out.println(ConsoleColor.CYAN + "Player [" + action.getPlayerId() + "] joined lobby [" + action.getGameLobbyId() + "]" + ConsoleColor.RESET);
        // TODO Communicate Join Ok

        if(lb.canStart()) {
            try {
                Server.getInstance().startGame(lb);
                System.out.println(ConsoleColor.GREEN + "[" + lb.getGameLobbyId() + "] lobby is full. Game started" + ConsoleColor.RESET);
                // TODO Communicate gameStart
            } catch (InvalidNewGameException e) {
                // TODO Communicate failing to launch new game
                throw new InvalidAction("JoinGame: Lobby was full but gameLaunch failed");
            }
        }
    }
}
