package it.polimi.ingsw.controller.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.action.GetGameAction;
import it.polimi.ingsw.action.JoinGameAction;
import it.polimi.ingsw.action.NewGameAction;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.model.power.PowerCard;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LobbyService {
    public static void newGame(NewGameAction action) throws InvalidAction {
        GameRules gameRules = action.getGameRules();
        if(gameRules == null) {
            try {
                gameRules = switch (action.getNumberOfPlayers()) {
                    case 2 -> GameRules.fromJson(new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json"))));
                    case 3 -> GameRules.fromJson(new String(Files.readAllBytes(Paths.get("src/main/resources/Rules3P.json"))));
                    case 4 -> GameRules.fromJson(new String(Files.readAllBytes(Paths.get("src/main/resources/Rules4P.json"))));
                    default -> throw new InvalidAction("NewGame: Invalid NumberOfPlayers without rules");
                };
            } catch (InvalidRulesException e) {
                throw new InvalidAction("NewGame: Server standard rules are corrupted");
            } catch (IOException e) {
                throw new InvalidAction("NewGame: Server failed to load standard rules");
            }

        }

        GameLobby lobby = new GameLobby(gameRules, action.getNumberOfPlayers());

        Server.getInstance().addNewGameLobby(lobby);
        System.out.println(ConsoleColor.CYAN + "New " + lobby.getLobbySize() + "P" + " gamelobby created [" + lobby.getGameLobbyId() + "]" + ConsoleColor.RESET);

        Server.getInstance().getClientNetHandler(action.getPlayerId()).send(lobby.getGameLobbyId()); // Replied with the lobby id
        joinGame(new JoinGameAction(action.getPlayerId(), lobby.getGameLobbyId(), action.getWizard()));
    }

    public static void joinGame(JoinGameAction action) throws InvalidAction {
        if(Server.getInstance().isWaitingInALobby(action.getPlayerId())) throw new InvalidAction("JoinGame : player already waiting in a lobby");

        GameLobby lb = Server.getInstance().getGameLobby(action.getGameLobbyId());

        if(lb == null) throw new InvalidAction("JoinGame: Invalid lobby ID");
        if(action.getWizard() == null) throw new InvalidAction("JoinGame: Invalid Wizard");

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

    public static void getGame(GetGameAction action) throws InvalidAction {
        Gson gson = new Gson();
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
        ClientNetworkHandler clientNetworkHandler = Server.getInstance().getClientNetHandler(action.getPlayerId());

        JsonElement jElem = gson.toJsonTree(gameHandler);
        JsonObject jObj = jElem.getAsJsonObject();
        jObj.addProperty("motherNatureIsOn", gameHandler.getGame().getMotherNature().isOn().getUuid());

        JsonArray powerCardsArray = new JsonArray();
        for(PowerCard powerCard : gameHandler.getGame().getPowerCards()) {
            powerCardsArray.add(gson.toJsonTree(powerCard, powerCard.getClass()));
        }
        JsonObject gameJObj = jObj.getAsJsonObject("game");
        gameJObj.add("powerCards", powerCardsArray);

        String gameHandlerSerializedInfo = gson.toJson(jObj);

        clientNetworkHandler.send(ActionHandler.toJson(new GetGameAction(action.getPlayerId(), gameHandlerSerializedInfo)));
        // returns MotherNature island position

    }
}
