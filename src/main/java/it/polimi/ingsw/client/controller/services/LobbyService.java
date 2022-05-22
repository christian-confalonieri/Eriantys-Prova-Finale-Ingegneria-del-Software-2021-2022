package it.polimi.ingsw.client.controller.services;

import com.google.gson.*;
import it.polimi.ingsw.action.*;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.controller.ClientActionHandler;
import it.polimi.ingsw.gui.GUILobbyController;
import it.polimi.ingsw.gui.GUIMainMenuController;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.MotherNature;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Team;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.Game4P;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.game.GameHandler4P;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.model.power.PowerCard;
import it.polimi.ingsw.server.GameLobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyService {
    public static void getGame(GetGameAction action) {
        Client.getInstance().setGameHandler(parseGameHandler(action.getGameHandlerInfoJson()));
        Client.getInstance().setClientState(ClientState.INGAME);
        Client.getInstance().getGui().notifyStateChange();
        Client.getInstance().setPollAllLobbies(false);
    }

    public static void getGameRequest() {
        Client.getInstance().getNetworkController().send(ClientActionHandler.toJson(new GetGameAction(Client.getInstance().getPlayerId())));
    }

    private static GameHandler parseGameHandler(String serializedGameHandlerInfo) {
        GameHandler gameHandler;
        Gson gson = new Gson();

        JsonElement jElem = JsonParser.parseString(serializedGameHandlerInfo);
        JsonObject jObj = jElem.getAsJsonObject();
        String motherNatureIsOnUUID = jObj.getAsJsonPrimitive("motherNatureIsOn").getAsString();
        jObj.remove("motherNatureIsOn");
        JsonArray powerCardsJsonArray = jObj.getAsJsonObject("game").getAsJsonArray("powerCards");

        boolean teams = jObj.getAsJsonObject("game").getAsJsonPrimitive("teams") != null;
        List<String> team0 = null;
        List<String> team1 = null;
        if (teams) {
            Game4P game4P = gson.fromJson(jObj.getAsJsonObject("game"), Game4P.class);
            gameHandler = gson.fromJson(jObj, GameHandler4P.class);
            ((GameHandler4P) gameHandler).set4PGame(game4P);

            team0 = new ArrayList<>();
            for(JsonElement j : jObj.getAsJsonObject("game").getAsJsonArray("team0Players")) {
                team0.add(j.getAsJsonPrimitive().getAsString());
            }
            team1 = new ArrayList<>();
            for(JsonElement j : jObj.getAsJsonObject("game").getAsJsonArray("team1Players")) {
                team1.add(j.getAsJsonPrimitive().getAsString());
            }
        }
        else {
            gameHandler = gson.fromJson(jObj, GameHandler.class);
        }

        // Retrieve gameHandler player references
        gameHandler.setCurrentPlayer(gameHandler.getGame().getPlayers().stream().filter(
                p -> p.getName().equals(gameHandler.getCurrentPlayer().getName())).findAny().get()
        );
        List<Player> orderedTurnPlayers = new ArrayList<>();
        for (Player p : gameHandler.getOrderedTurnPlayers()) {
            orderedTurnPlayers.add(gameHandler.getGame().getPlayers().stream().filter(
                    pl -> pl.getName().equals(p.getName())).findAny().get()
            );
        }
        gameHandler.setOrderedTurnPlayers(orderedTurnPlayers);
        gameHandler.setFirstTurnPlayer(gameHandler.getGame().getPlayers().stream().filter(
                p -> p.getName().equals(gameHandler.getFirstTurnPlayer().getName())).findAny().get()
        );

        // Retrieve islands neighbords references
        for (Island island : gameHandler.getGame().getIslands()) {
            island.setNextIsland(gameHandler.getGame().getNextIsland(island));
            island.setPrevIsland(gameHandler.getGame().getPrevIsland(island));
        }

        // Retrieve power references
        gameHandler.getGame().getEffectHandler().setEffectPlayer(
                gameHandler.getGame().getEffectHandler().getEffectPlayer() == null ? null : gameHandler.getCurrentPlayer());
        List<PowerCard> powerCards = new ArrayList<>();
        for(JsonElement jsonElement : powerCardsJsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            PowerType type = PowerType.valueOf(jsonObject.getAsJsonPrimitive("type").getAsString());
            PowerCard p = gson.fromJson(jsonElement, PowerCard.getClassFromType(type).getClass());
            p.setGameHandler(gameHandler);
            powerCards.add(p);
        }
        gameHandler.getGame().setPowerCards(powerCards);

        // Retrieve tower owner references
        gameHandler.getGame().getPlayers().forEach(p -> {
            if (p.getSchool().getTowers() != null)
                p.getSchool().getTowers().forEach(t -> t.setOwner(p));
        });
        gameHandler.getGame().getIslands().forEach(i -> i.getTowers().forEach(t -> {
            t.setOwner(gameHandler.getGame().getPlayers().stream().filter(p -> p.getTowerColor().equals(t.getColor())).findAny().get());
        }));

        // Create motherNature
        gameHandler.getGame().setMotherNature(new MotherNature(gameHandler.getGame().getIslandFromId(motherNatureIsOnUUID)));

        // Build teams (if 4P)
        if (teams) {
            Game4P game4P = (Game4P) gameHandler.getGame();
            List<Player> team0P = team0.stream().map(game4P::getPlayerFromId).toList();
            List<Player> team1P = team1.stream().map(game4P::getPlayerFromId).toList();

            Team team0T = new Team(team0P);
            Team team1T = new Team(team1P);
            List<Team> teamsArray = new ArrayList<>();
            teamsArray.add(team0T);
            teamsArray.add(team1T);

            game4P.setTeams(teamsArray);
        }


        return gameHandler;
    }


    public static void getAllLobbys(GetAllLobbysAction action) {
        Client.getInstance().setAllServerLobbys(action.getGameLobbyList());
        if(Client.getInstance().getGui() != null) {// TODO Not the best solution, but it's the only method to cal the gui before its cration
            Client.getInstance().getGui().guiCallMainMenu(GUIMainMenuController::updateLobbies);
            for(GameLobby gameLobby : Client.getInstance().getAllServerLobbys()) {
                Client.getInstance().getGui().guiCallLobby(GUILobbyController -> GUILobbyController.updateConnectedPlayers(gameLobby));
            }
        }
    }

    public static void getAllLobbysRequest() {
        Client.getInstance().getNetworkController().send(ClientActionHandler.toJson(new GetAllLobbysAction(Client.getInstance().getPlayerId())));
    }


    public static void newGameRequest(int numberOfPlayers, GameRules gameRules, Wizard wizard) {
        Client.getInstance().getNetworkController().send(ClientActionHandler.toJson(
                new NewGameAction(Client.getInstance().getPlayerId(), numberOfPlayers, gameRules, wizard)));
    }

    public static void newGameOk(ACK ack) {

    }

    public static void newGameFailed(ACK ack) {
        System.out.println(ConsoleColor.RED + ack.getMessage() + ConsoleColor.RESET);
    }


    public static void joinGame(JoinGameAction action) {
        Client.getInstance().setGameLobby(action.getNewGameLobby());
        Client.getInstance().setClientState(ClientState.WAITINGLOBBY);
        Client.getInstance().getGui().notifyStateChange();
    }

    public static void joinGameRequest(int numberOfPlayers, Wizard wizard) {
        Client.getInstance().getNetworkController().send(ClientActionHandler.toJson(
                new JoinGameAction(Client.getInstance().getPlayerId(), numberOfPlayers, wizard)
        ));
    }

    public static void joinGameIdRequest(String id, Wizard wizard) {
        Client.getInstance().getNetworkController().send(ClientActionHandler.toJson(
                new JoinGameAction(Client.getInstance().getPlayerId(), id, wizard)
        ));
    }

    public static void joinGameFailed(ACK ack) {
        if(ack.getMessage().contains("Invalid lobby ID")) {
            System.out.println(ConsoleColor.YELLOW + "No lobby available. Try creating a new one with \"newgame\""+ ConsoleColor.RESET);
            Client.getInstance().getGui().guiCallMainMenu(guiMainMenuController -> guiMainMenuController.errorLabelWrite("No lobby available. Try creating a new one with \"newgame\""));
        }
        else {
            System.out.println(ConsoleColor.RED + ack.getMessage() + ConsoleColor.RESET);
            Client.getInstance().getGui().guiCallMainMenu(guiMainMenuController -> guiMainMenuController.errorLabelWrite(ack.getMessage()));
        }
    }




}
