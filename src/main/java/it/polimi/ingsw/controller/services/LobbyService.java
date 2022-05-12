package it.polimi.ingsw.controller.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.action.*;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.model.power.PowerCard;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.PlayerLobby;
import it.polimi.ingsw.server.Server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class LobbyService {
    /**
     * @author Leonardo Airoldi, Christian Confalonieri
     */
    public static void newGame(NewGameAction action) throws InvalidAction {
        GameRules gameRules = action.getGameRules();
        ClassLoader classLoader = LobbyService.class.getClassLoader();
        if(gameRules == null) {
            /*
             * The loading of the rules depends on 3 factors:
             * 1) The server .jar file must be in the repo (in this case it is in the "out" folder).
             * 2) The .json file of the rules must be in "src/main/resources/"
             * 3) The name of the repo must be "ingsw2022-AM03"
             * */
            String[] absolutePath;
            String disk,path,ruleJson,repoName = "ingsw2022-AM03";
            try {
                ruleJson = switch (action.getNumberOfPlayers()) {
                    case 2 -> "Rules2P.json";
                    case 3 -> "Rules3P.json";
                    case 4 -> "Rules4P.json";
                    default -> {
                        Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.NEWGAME, "NewGame: Invalid NumberOfPlayers without rules", false)));
                        throw new InvalidAction("NewGame: Invalid NumberOfPlayers without rules");
                    }
                };
//                if(!(new File(classLoader.getResource(ruleJson).getFile())).getPath().split(":")[1].contains("\\target\\classes")) {
//                    // Case of starting the server from .jar file
//                    absolutePath = (new File(classLoader.getResource(ruleJson).getFile())).getPath().split(repoName)[0].split(":");
//                    disk = absolutePath[absolutePath.length-2].split("\\\\")[1] + ":";
//                    path = disk + absolutePath[absolutePath.length-1] + repoName + "/src/main/resources/" + ruleJson;
//                }
//                else {
                    // Case of starting the server from intellij
                    //path = (new File(classLoader.getResource(ruleJson).getFile())).getPath();
//                }
//                String json = new String(Files.readAllBytes(Paths.get(path)));

                InputStream inputStream = classLoader.getResourceAsStream(ruleJson);
                String json =  new Scanner(inputStream).useDelimiter("\\A").next();
                gameRules = GameRules.fromJson(json);
            } catch (InvalidRulesException e) {
                Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.NEWGAME, "NewGame: Server standard rules are corrupted", false)));
                throw new InvalidAction("NewGame: Server standard rules are corrupted");
            }
//            } catch (IOException e) {
//                Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.NEWGAME, "NewGame: Server failed to load standard rules", false)));
//                throw new InvalidAction("NewGame: Server failed to load standard rules");
//            }

        }

        GameLobby lobby = new GameLobby(gameRules, action.getNumberOfPlayers());
        Server.getInstance().addNewGameLobby(lobby);
        System.out.println(ConsoleColor.CYAN + "New " + lobby.getLobbySize() + "P" + " gamelobby created [" + lobby.getGameLobbyId() + "]" + ConsoleColor.RESET);
        Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.NEWGAME, "NewGame: succesfully created", true)));

        joinGame(new JoinGameAction(action.getPlayerId(), lobby.getGameRules().cloudsRules.numberOfClouds , action.getWizard()));
    }

    public static void joinGame(JoinGameAction action) throws InvalidAction {
        if(Server.getInstance().isWaitingInALobby(action.getPlayerId())) {
            Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(
                    new ACK(action.getPlayerId(), ActionType.JOINGAME, "JoinGame : player already waiting in a lobby", false)));
            throw new InvalidAction("JoinGame : player already waiting in a lobby");
        }


        GameLobby lb = null;
        for(GameLobby gameLobby : Server.getInstance().getAllGameLobbys()) {
            if(gameLobby.getGameRules().cloudsRules.numberOfClouds == action.getNumberOfPlayers()) {
                lb = gameLobby;
                break;
            }
        }

        if(lb == null) {
            Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(
                    new ACK(action.getPlayerId(), ActionType.JOINGAME, "JoinGame: Invalid lobby ID", false)));
            throw new InvalidAction("JoinGame: Invalid lobby ID");
        }
        if(action.getWizard() == null) {
            Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(
                    new ACK(action.getPlayerId(), ActionType.JOINGAME, "JoinGame: Invalid Wizard", false)));
            throw new InvalidAction("JoinGame: Invalid Wizard");
        }

        boolean hasBeenAdded = lb.addPlayer(action.getPlayerId(), action.getWizard());
        if(!hasBeenAdded) {
            Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(
                    new ACK(action.getPlayerId(), ActionType.JOINGAME, "JoinGame: Name or Wizard Already Taken", false)));
            throw new InvalidAction("JoinGame: Name or Wizard Already Taken");
        }

        System.out.println(ConsoleColor.CYAN + "Player [" + action.getPlayerId() + "] joined lobby [" + lb.getGameLobbyId() + "]" + ConsoleColor.RESET);

        action.setNewGameLobby(lb);
        Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(action));

        if(lb.canStart()) {
            try {
                Server.getInstance().startGame(lb);
                System.out.println(ConsoleColor.GREEN + "[" + lb.getGameLobbyId() + "] lobby is full. Game started" + ConsoleColor.RESET);
                for (String n : Server.getInstance().getGameHandler(action.getPlayerId()).getGame().getPlayers().stream().map(Player::getName).toList()) {
                    getGame(new GetGameAction(n));
                }
            } catch (InvalidNewGameException e) {
                for (String name : lb.getPlayersWaiting().stream().map(PlayerLobby::getPlayerId).toList()) {
                    Server.getInstance().getClientNetHandler(name).send(ActionHandler.toJson(
                            new ACK(name, ActionType.JOINGAME, "JoinGame: Lobby was full but gameLaunch failed", false)));
                }
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

    public static void getAllLobbys(GetAllLobbysAction action) {
        action.setGameLobbyList(Server.getInstance().getAllGameLobbys());
        Server.getInstance().getClientNetHandler(action.getPlayerId()).send(ActionHandler.toJson(action));
    }

}
