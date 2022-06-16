package it.polimi.ingsw.client.controller.services;


import com.google.gson.Gson;
import it.polimi.ingsw.action.*;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.gui.GUIGameController;
import it.polimi.ingsw.model.entity.Cloud;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.power.EffectHandler;
import it.polimi.ingsw.model.power.PowerCard;

import java.util.List;
import java.util.Map;

public class GameService {
    /**
     * Method that plays the card on the client model and advances the state
     * @param action The action received from the server
     * @author Christian Confalonieri
     */
    public static void playCard(PlayCardAction action) {
        GameHandler gameHandler = Client.getInstance().getGameHandler();
        gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());
        gameHandler.advance();
        Client.getInstance().getGui().guiCallGame(GUIGameController::render);
    }

    /**
     * Method that sends the request to the server in order to play the card
     * @param card The card to be played on the client model
     * @author Christian Confalonieri
     */
    public static void playCardRequest(Card card) {
        Gson gson = new Gson();
        Client.getInstance().getNetworkController().send(gson.toJson(new PlayCardAction(Client.getInstance().getPlayerId(),card)));
    }

    /**
     * Print the error message on the screen
     * @param failedAck The ack of action failure
     * @author Christian Confalonieri
     */
    public static void failedPlayCard(ACK failedAck) {
        System.out.println(ConsoleColor.YELLOW + failedAck.getMessage() + ConsoleColor.RESET);
        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.errorWrite(failedAck.getMessage()));
    }

    /**
     * Method that move students on the client model and advances the state
     * @param action The action received from the server
     * @author Christian Confalonieri
     */
    public static void moveStudents(MoveStudentsAction action) {
        GameHandler gameHandler = Client.getInstance().getGameHandler();

        List<Student> studentsToDiningRoom = action.getToDiningRoom();
        Map<Student,String> studentsToIsland = action.getToIsland();

        if(studentsToDiningRoom != null) {
            for(Student student : studentsToDiningRoom) {
                gameHandler.getCurrentPlayer().setCoins(gameHandler.getCurrentPlayer().getCoins() + gameHandler.getCurrentPlayer().getSchool().entranceToDiningRoom(student));
            }
            gameHandler.getGame().professorRelocate();
        }


        if(studentsToIsland != null) {
            for(Student student : studentsToIsland.keySet()) {
                gameHandler.getCurrentPlayer().getSchool().entranceToIsland(student,gameHandler.getGame().getIslandFromId(studentsToIsland.get(student)));
            }
        }
        gameHandler.advance();
        Client.getInstance().getGui().guiCallGame(GUIGameController::render);
    }

    /**
     * Method that sends the request to the server in order to move students
     * @param toDiningRoom Students to be moved to the dining room
     * @param toIslands Students to be moved to the islands
     * @author Christian Confalonieri
     */
    public static void moveStudentsRequest(List<Student> toDiningRoom, Map<Student,String> toIslands) {
        Gson gson = new Gson();
        Client.getInstance().getNetworkController().send(gson.toJson(new MoveStudentsAction(Client.getInstance().getPlayerId(),toDiningRoom,toIslands)));
    }

    /**
     * Print the error message on the screen
     * @param failedAck The ack of action failure
     * @author Christian Confalonieri
     */
    public static void failedMoveStudents(ACK failedAck) {
        System.out.println(ConsoleColor.YELLOW + failedAck.getMessage() + ConsoleColor.RESET);
        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.errorWrite(failedAck.getMessage()));
    }

    /**
     * Method that move Mother Nature on the client model and advances the state
     * @param action The action received from the server
     * @author Christian Confalonieri
     */
    public static void moveMotherNature(MoveMotherNatureAction action) {
        GameHandler gameHandler = Client.getInstance().getGameHandler();

        gameHandler.getGame().getMotherNature().move(action.getSteps());
        Island currentIsland = gameHandler.getGame().getMotherNature().isOn();
        gameHandler.getGame().conquerIsland(currentIsland);

        if(currentIsland.checkUnifyNext()) {
            gameHandler.getGame().unifyIsland(currentIsland,currentIsland.getNextIsland());
        }

        if(currentIsland.checkUnifyPrev()) {
            gameHandler.getGame().unifyIsland(currentIsland,currentIsland.getPrevIsland());
        }
        gameHandler.advance();
        Client.getInstance().getGui().guiCallGame(GUIGameController::render);
    }

    /**
     * Method that sends the request to the server in order to move Mother Nature
     * @param steps Steps to be taken by mother nature
     * @author Christian Confalonieri
     */
    public static void moveMotherNatureRequest(int steps) {
        Gson gson = new Gson();
        Client.getInstance().getNetworkController().send(gson.toJson(new MoveMotherNatureAction(Client.getInstance().getPlayerId(),steps)));
    }

    /**
     * Print the error message on the screen
     * @param failedAck The ack of action failure
     * @author Christian Confalonieri
     */
    public static void failedMoveMotherNature(ACK failedAck) {
        System.out.println(ConsoleColor.YELLOW + failedAck.getMessage() + ConsoleColor.RESET);
        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.errorWrite(failedAck.getMessage()));
    }

    /**
     * Method that moves cloud students on the client model and advances the state
     * @param action The action received from the server
     * @author Christian Confalonieri
     */
    public static void moveCloud(MoveCloudAction action){
        GameHandler gameHandler = Client.getInstance().getGameHandler();
        Cloud cloud = gameHandler.getGame().getCloudFromId(action.getCloudUUID());
        List<Student> students = cloud.pickAllStudents();
        for(Student student : students) {
            gameHandler.getCurrentPlayer().getSchool().addEntrance(student);
        }
        gameHandler.advance();
        Client.getInstance().getGui().guiCallGame(GUIGameController::render);
    }

    /**
     * Method that sends the request to the server in order to move the cloud students
     * @param cloud The chosen cloud
     * @author Christian Confalonieri
     */
    public static void moveCloudRequest(Cloud cloud){
        Gson gson = new Gson();
        Client.getInstance().getNetworkController().send(gson.toJson(new MoveCloudAction(Client.getInstance().getPlayerId(), cloud.getUuid())));
    }

    /**
     * Print the error message on the screen
     * @param failedAck The ack of action failure
     * @author Christian Confalonieri
     */
    public static void failedMoveCloud(ACK failedAck) {
        System.out.println(ConsoleColor.YELLOW + failedAck.getMessage() + ConsoleColor.RESET);
        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.errorWrite(failedAck.getMessage()));
    }

    /**
     * Method that activates the chosen character on the client model and advances the state
     * @param action The action received from the server
     * @author Christian Confalonieri
     */
    public static void power(PowerAction action) {
        GameHandler gameHandler = Client.getInstance().getGameHandler();
        PowerCard powerCard = gameHandler.getGame().getPowerCard(action.getType());

        gameHandler.getGame().setEffectHandler(action.getEffectHandler());

        powerCard.power();
        Client.getInstance().getGui().guiCallGame(GUIGameController::render);
    }

    /**
     * Method that sends the request to the server in order to play the chosen character
     * @param effectHandler The effectHandler with the appropriately modified attributes
     * @author Christian Confalonieri
     */
    public static void powerRequest(PowerType type, EffectHandler effectHandler) {
        Gson gson = new Gson();
        Client.getInstance().getNetworkController().send(gson.toJson(new PowerAction(Client.getInstance().getPlayerId(),type,effectHandler)));
    }

    /**
     * Print the error message on the screen
     * @param failedAck The ack of action failure
     * @author Christian Confalonieri
     */
    public static void failedPower(ACK failedAck) {
        System.out.println(ConsoleColor.YELLOW + failedAck.getMessage() + ConsoleColor.RESET);
        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.errorWrite(failedAck.getMessage()));
    }

    public static void gameInterrupted(GameInterruptedAction action) {
        Client.getInstance().setFinalLeaderBoard(action.getLeaderBoard());
        Client.getInstance().setClientState(ClientState.ENDGAME);
        Client.getInstance().getCli().render();

        Client.getInstance().getGui().notifyStateChange();
        Client.getInstance().getGui().guiCallScoreboard(guiScoreboardController -> guiScoreboardController.renderScoreBoard(action.getLeaderBoard()));
    }
}
