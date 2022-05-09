package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.*;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.entity.Cloud;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.TurnPhase;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.power.PowerCard;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;

import java.util.List;
import java.util.Map;

public class GameService {
    public static void playCard(PlayCardAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());

        if (gameHandler.getGamePhase().equals(GamePhase.PREPARATION)) {
            if(gameHandler.previousPlayedCards().contains(action.getPlayedCard())) {
                if(gameHandler.previousPlayedCards().containsAll(gameHandler.getCurrentPlayer().getHandCards())) { // all your cards are already played: no alternative
                    gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());
                    gameHandler.advance();

                    //send changes to all players
                    List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
                    for(ClientNetworkHandler broadcastClient : broadcastClients) {
                        broadcastClient.send(ActionHandler.toJson(action));
                    }
                    System.out.println(action.getPlayerId() + " played successfully the card with number: " + action.getPlayedCard().getNumber());
                }
                else { // invalid card (you can play other valid cards)
                    //Ignore and communicate invalid card
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.PLAYCARD, "card already played",false)));
                    throw new InvalidAction("PlayCardAction: card already played");
                }
            }
            else { // valid card (nobody played this card in this turn)
                gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());
                gameHandler.advance();

                //send changes to all players
                List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
                for(ClientNetworkHandler broadcastClient : broadcastClients) {
                    broadcastClient.send(ActionHandler.toJson(action));
                }
                System.out.println(action.getPlayerId() + " played successfully the card with number: " + action.getPlayedCard().getNumber());
            }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    public static void moveStudents(MoveStudentsAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());

        if (gameHandler.getGamePhase().equals(GamePhase.TURN) &&
                gameHandler.getTurnPhase().equals(TurnPhase.MOVESTUDENTS)) {
            List<Student> studentsToDiningRoom = action.getToDiningRoom();
            Map<Student,String> studentsToIsland = action.getToIsland();

            if(studentsToDiningRoom == null && studentsToIsland == null) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "invalid lists",false)));
                throw new InvalidAction("MoveStudentsAction: invalid lists");
            }

            if(studentsToDiningRoom.size() == 0 && studentsToIsland.size() == 0) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "invalid lists",false)));
                throw new InvalidAction("MoveStudentsAction: invalid lists");
            }

            if(studentsToIsland.size() + studentsToDiningRoom.size() !=
                    gameHandler.getGame().getGameRules().studentsRules.turnStudents) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "invalid number of students",false)));
                throw new InvalidAction("MoveStudentsAction: invalid number of students");
            }


            if(!gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(studentsToDiningRoom) &&
            !gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(studentsToIsland.keySet())) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "invalid students in the lists",false)));
                throw new InvalidAction("MoveStudentsAction: invalid students in the lists");
            }


            if(studentsToDiningRoom != null) {
                for(Student student : studentsToDiningRoom) {
                    gameHandler.getCurrentPlayer().getSchool().entranceToDiningRoom(student);
                }
                gameHandler.getGame().professorRelocate();
                System.out.println("Students moved to dining room and professors relocated");
            }

            if(studentsToIsland != null) {
                for(Student student : studentsToIsland.keySet()) {
                    gameHandler.getCurrentPlayer().getSchool().entranceToIsland(student,gameHandler.getGame().getIslandFromId(studentsToIsland.get(student)));
                }
                System.out.println("Students moved to islands");
            }
        }
        else {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "invalid phase",false)));
            throw new InvalidAction("MoveStudentsAction: invalid phase");
        }
        gameHandler.advance();
        //send changes to all players
        List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
        for(ClientNetworkHandler broadcastClient : broadcastClients) {
            broadcastClient.send(ActionHandler.toJson(action));
        }
    }

    /**
     * @author Christian Confalonieri
     */
    public static void moveMotherNature(MoveMotherNatureAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());

        if (gameHandler.getGamePhase().equals(GamePhase.TURN) &&
                gameHandler.getTurnPhase().equals(TurnPhase.MOVEMOTHER)) {

            int maxMovements;
            if(gameHandler.getGame().getEffectHandler().isMailmanActive()) {
                maxMovements = gameHandler.getCurrentPlayer().getLastPlayedCard().getMovements() + 2;
            }
            else {
                maxMovements = gameHandler.getCurrentPlayer().getLastPlayedCard().getMovements();
            }

            if(action.getSteps() > 0 &&
                    action.getSteps() <= maxMovements) {

                gameHandler.getGame().getMotherNature().move(action.getSteps());
                Island currentIsland = gameHandler.getGame().getMotherNature().isOn();
                gameHandler.getGame().conquerIsland(currentIsland);
                System.out.println("mother nature has been moved");

                if(currentIsland.checkUnifyNext()) {
                    gameHandler.getGame().unifyIsland(currentIsland,currentIsland.getNextIsland());
                    System.out.println("The next island has been unified");
                }

                if(currentIsland.checkUnifyPrev()) {
                    gameHandler.getGame().unifyIsland(currentIsland,currentIsland.getPrevIsland());
                    System.out.println("The prev island has been unified");
                }
            }
            else {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVEMOTHERNATURE, "invalid number of steps",false)));
                throw new InvalidAction("MoveMotherNatureAction: invalid number of steps");
            }
        }
        else {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVEMOTHERNATURE, "invalid phase",false)));
            throw new InvalidAction("MoveMotherNatureAction: invalid phase");
        }
        gameHandler.advance();
        //send changes to all players
        List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
        for(ClientNetworkHandler broadcastClient : broadcastClients) {
            broadcastClient.send(ActionHandler.toJson(action));
        }
    }

    /**
     * @author Christian Confalonieri
     */
    public static void moveCloud(MoveCloudAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());

        if (gameHandler.getGamePhase().equals(GamePhase.TURN) &&
                gameHandler.getTurnPhase().equals(TurnPhase.MOVEFROMCLOUD)) {
            Cloud cloud = gameHandler.getGame().getCloudFromId(action.getCloudUUID());
            List<Student> students = cloud.pickAllStudents();
            for(Student student : students) {
                gameHandler.getCurrentPlayer().getSchool().addEntrance(student);
            }
            System.out.println("students moved from the cloud to the entrance");
        }
        else {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVECLOUD, "invalid phase",false)));
            throw new InvalidAction("MoveCloudAction: invalid phase");
        }
        gameHandler.advance();
        //send changes to all players
        List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
        for(ClientNetworkHandler broadcastClient : broadcastClients) {
            broadcastClient.send(ActionHandler.toJson(action));
        }
    }

    /**
     * @author Christian Confalonieri
     */
    public static void power(PowerAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
        PowerCard powerCard = gameHandler.getGame().getPowerCard(action.getType());

        if(gameHandler.getCurrentPlayer().getCoins() < powerCard.getCost()) {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "the player does not have enough coins to pay for the activation of this power",false)));
            throw new InvalidAction("Power: the player does not have enough coins to pay for the activation of this power");
        }

        if(gameHandler.getGame().getEffectHandler().isEffectActive()) {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "another power has already been activated",false)));
            throw new InvalidAction("Power: another power has already been activated");
        }

        if(!gameHandler.getGame().getPowerCards().contains(powerCard)) {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "invalid power",false)));
            throw new InvalidAction("Power: invalid power");
        }

        if(action.getType() == null) {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "invalid power type",false)));
            throw new InvalidAction("Power: invalid power type");
        }

        if(action.getEffectHandler() == null) {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "invalid effectHandler",false)));
            throw new InvalidAction("Power: invalid effectHandler");
        }

        if (gameHandler.getGamePhase().equals(GamePhase.TURN)) {
            gameHandler.getGame().setEffectHandler(action.getEffectHandler());

            powerCard.power();
            System.out.println(powerCard.getType().toString().toLowerCase() + " power activated");

            //send changes to all players
            List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
            for(ClientNetworkHandler broadcastClient : broadcastClients) {
                broadcastClient.send(ActionHandler.toJson(action));
            }
        }
        else {
            Server.getInstance().getClientNetHandler(action.getPlayerId())
                    .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "invalid phase",false)));
            throw new InvalidAction("Power: invalid phase");
        }
    }
}
