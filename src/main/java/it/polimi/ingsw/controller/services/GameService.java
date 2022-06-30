package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.*;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.TurnPhase;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.power.PowerCard;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameService {
    public static void playCard(PlayCardAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
        synchronized (gameHandler) {
            if (gameHandler.getGamePhase().equals(GamePhase.PREPARATION)) {
                if (!gameHandler.getGame().getPlayerFromId(action.getPlayerId()).getHandCards().contains(action.getPlayedCard())) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.PLAYCARD, "Card is not in your deck", false)));
                    throw new InvalidAction("PlayCardAction: card was not in player deck");
                }

                if (gameHandler.previousPlayedCards().contains(action.getPlayedCard())) {
                    if (gameHandler.previousPlayedCards().containsAll(gameHandler.getCurrentPlayer().getHandCards())) { // all your cards are already played: no alternative
                        gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());
                        gameHandler.advance();

                        //send changes to all players
                        List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
                        for (ClientNetworkHandler broadcastClient : broadcastClients) {
                            broadcastClient.send(ActionHandler.toJson(action));
                        }
                        System.out.println(action.getPlayerId() + " played successfully the card with number: " + action.getPlayedCard().getNumber());
                    } else { // invalid card (you can play other valid cards)
                        //Ignore and communicate invalid card
                        Server.getInstance().getClientNetHandler(action.getPlayerId())
                                .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.PLAYCARD, "card already played", false)));
                        throw new InvalidAction("PlayCardAction: card already played");
                    }
                } else { // valid card (nobody played this card in this turn)
                    gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());
                    gameHandler.advance();

                    //send changes to all players
                    List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
                    for (ClientNetworkHandler broadcastClient : broadcastClients) {
                        broadcastClient.send(ActionHandler.toJson(action));
                    }
                    System.out.println(action.getPlayerId() + " played successfully the card with number: " + action.getPlayedCard().getNumber());
                }
                if (gameHandler.isEnded()) {
                    notifyAndCloseGameEnd(gameHandler, null);
                }
            }
        }
    }

    /**
     * Method that moves students to the server model of the current game and advances the state.
     * Before changing the model it does all the checks and in case it sends an ack.
     * If successful (not failed ack) update all clients.
     * @param action The action received from the client
     * @throws InvalidAction
     * @author Christian Confalonieri
     */
    public static void moveStudents(MoveStudentsAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
        synchronized (gameHandler) {
            if (gameHandler.getGamePhase().equals(GamePhase.TURN) &&
                    gameHandler.getTurnPhase().equals(TurnPhase.MOVESTUDENTS)) {
                List<Student> studentsToDiningRoom = action.getToDiningRoom();
                Map<Student, String> studentsToIsland = action.getToIsland();

                if (studentsToDiningRoom == null && studentsToIsland == null) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "Invalid lists", false)));
                    throw new InvalidAction("MoveStudentsAction: invalid lists");
                }

                if (studentsToDiningRoom.size() == 0 && studentsToIsland.size() == 0) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "Invalid lists", false)));
                    throw new InvalidAction("MoveStudentsAction: invalid lists");
                }

                if (studentsToIsland.size() + studentsToDiningRoom.size() !=
                        gameHandler.getGame().getGameRules().studentsRules.turnStudents) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "Invalid number of students", false)));
                    throw new InvalidAction("MoveStudentsAction: invalid number of students");
                }


                if (!gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(studentsToDiningRoom) ||
                        !gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(studentsToIsland.keySet())) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "Invalid students in the lists", false)));
                    throw new InvalidAction("MoveStudentsAction: invalid students in the lists");
                }

                if (studentsToIsland.keySet().stream().anyMatch(studentsToDiningRoom::contains)) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "Duplicate student to island and to dining room", false)));
                    throw new InvalidAction("MoveStudentsAction: Duplicate student to island and to dining room");
                }


                if(laneOverflowCheck(gameHandler,action)) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "At least one of the students entered cannot be added to the dining room", false)));
                    throw new InvalidAction("MoveStudentsAction: At least one of the students entered cannot be added to the dining room");
                }

                if (studentsToDiningRoom != null) {
                    for (Student student : studentsToDiningRoom) {
                        gameHandler.getCurrentPlayer().setCoins(gameHandler.getCurrentPlayer().getCoins() + gameHandler.getCurrentPlayer().getSchool().entranceToDiningRoom(student));
                    }
                    gameHandler.getGame().professorRelocate();
                    System.out.println("Students moved to dining room and professors relocated");
                }

                if (studentsToIsland != null) {
                    for (Student student : studentsToIsland.keySet()) {
                        gameHandler.getCurrentPlayer().getSchool().entranceToIsland(student, gameHandler.getGame().getIslandFromId(studentsToIsland.get(student)));
                    }
                    System.out.println("Students moved to islands");
                }
            } else {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVESTUDENTS, "invalid phase", false)));
                throw new InvalidAction("MoveStudentsAction: invalid phase");
            }
            gameHandler.advance();
            //send changes to all players
            List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
            for (ClientNetworkHandler broadcastClient : broadcastClients) {
                broadcastClient.send(ActionHandler.toJson(action));
            }
            if (gameHandler.isEnded()) {
                notifyAndCloseGameEnd(gameHandler, null);
            }
        }
    }

    /**
     * Method that moves Mother Nature to the server model of the current game and advances the state.
     * Before changing the model it does all the checks and in case it sends an ack.
     * If successful (not failed ack) update all clients.
     * @param action The action received from the client
     * @throws InvalidAction
     * @author Christian Confalonieri
     */
    public static void moveMotherNature(MoveMotherNatureAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
        synchronized (gameHandler) {
            if (gameHandler.getGamePhase().equals(GamePhase.TURN) &&
                    gameHandler.getTurnPhase().equals(TurnPhase.MOVEMOTHER)) {

                int maxMovements;
                if (gameHandler.getGame().getEffectHandler().isMailmanActive()) {
                    maxMovements = gameHandler.getCurrentPlayer().getLastPlayedCard().getMovements() + 2;
                } else {
                    maxMovements = gameHandler.getCurrentPlayer().getLastPlayedCard().getMovements();
                }

                if (action.getSteps() > 0 &&
                        action.getSteps() <= maxMovements) {

                    gameHandler.getGame().getMotherNature().move(action.getSteps());
                    Island currentIsland = gameHandler.getGame().getMotherNature().isOn();
                    gameHandler.getGame().conquerIsland(currentIsland);
                    System.out.println("mother nature has been moved");

                    if (currentIsland.checkUnifyNext()) {
                        gameHandler.getGame().unifyIsland(currentIsland, currentIsland.getNextIsland());
                        System.out.println("The next island has been unified");
                    }

                    if (currentIsland.checkUnifyPrev()) {
                        gameHandler.getGame().unifyIsland(currentIsland, currentIsland.getPrevIsland());
                        System.out.println("The prev island has been unified");
                    }
                } else {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVEMOTHERNATURE, "invalid number of steps", false)));
                    throw new InvalidAction("MoveMotherNatureAction: invalid number of steps");
                }
            } else {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVEMOTHERNATURE, "invalid phase", false)));
                throw new InvalidAction("MoveMotherNatureAction: invalid phase");
            }
            gameHandler.advance();
            //send changes to all players
            List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
            for (ClientNetworkHandler broadcastClient : broadcastClients) {
                broadcastClient.send(ActionHandler.toJson(action));
            }
            if (gameHandler.isEnded()) {
                notifyAndCloseGameEnd(gameHandler, null);
            }
        }
    }

    /**
     * Method that moves cloud students to the server model of the current game and advances the state.
     * Before changing the model it does all the checks and in case it sends an ack.
     * If successful (not failed ack) update all clients.
     * @param action The action received from the client
     * @throws InvalidAction
     * @author Christian Confalonieri
     */
    public static void moveCloud(MoveCloudAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
        synchronized (gameHandler) {
            if (gameHandler.getGamePhase().equals(GamePhase.TURN) &&
                    gameHandler.getTurnPhase().equals(TurnPhase.MOVEFROMCLOUD)) {

                Cloud cloud = gameHandler.getGame().getCloudFromId(action.getCloudUUID());

                if (cloud.getStudents().isEmpty()) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVECLOUD, "Invalid cloud selected", false)));
                    throw new InvalidAction("MoveCloudAction: invalid cloud");
                }

                List<Student> students = cloud.pickAllStudents();
                for (Student student : students) {
                    gameHandler.getCurrentPlayer().getSchool().addEntrance(student);
                }
                System.out.println("students moved from the cloud to the entrance");
            } else {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.MOVECLOUD, "invalid phase", false)));
                throw new InvalidAction("MoveCloudAction: invalid phase");
            }
            gameHandler.advance();
            //send changes to all players
            List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
            for (ClientNetworkHandler broadcastClient : broadcastClients) {
                broadcastClient.send(ActionHandler.toJson(action));
            }
            if (gameHandler.isEnded()) {
                notifyAndCloseGameEnd(gameHandler, null);
            }
        }
    }

    /**
     * Method that activates the chosen character to the server model of the current game and advances the state.
     * Before changing the model it does all the checks and in case it sends an ack.
     * If successful (not failed ack) update all clients.
     * @param action The action received from the client
     * @throws InvalidAction
     * @author Christian Confalonieri
     */
    public static void power(PowerAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
        synchronized (gameHandler) {
            PowerCard powerCard = gameHandler.getGame().getPowerCard(action.getType());

            if (action.getType() == null) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "invalid power type", false)));
                throw new InvalidAction("Power: invalid power type");
            }

            if (!gameHandler.getGame().getPowerCards().contains(powerCard)) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "invalid power", false)));
                throw new InvalidAction("Power: invalid power");
            }

            if (gameHandler.getGame().getEffectHandler().isEffectActive()) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "another power has already been activated", false)));
                throw new InvalidAction("Power: another power has already been activated");
            }

            if (gameHandler.getCurrentPlayer().isPlayedPowerThisTurn()) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "power already played in this turn", false)));
                throw new InvalidAction("Power: power already played in this turn");
            }

            if (action.getEffectHandler() == null) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "invalid effectHandler", false)));
                throw new InvalidAction("Power: invalid effectHandler");
            }

            if (gameHandler.getCurrentPlayer().getCoins() < powerCard.getCost()) {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "the player does not have enough coins to pay for the activation of this power", false)));
                throw new InvalidAction("Power: the player does not have enough coins to pay for the activation of this power");
            }

            if(action.getType() == PowerType.FRIAR || action.getType() == PowerType.PRINCESS) {
                if(action.getEffectHandler().getChosenStudents1().size() != 1) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "Invalid number of students", false)));
                    throw new InvalidAction("Power: Invalid number of students");
                }
            }

            if(action.getType() == PowerType.PRINCESS) {
                if(laneOverflowCheck(gameHandler,action)) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "At least one of the students entered cannot be added to the dining room", false)));
                    throw new InvalidAction("Power: At least one of the students entered cannot be added to the dining room");
                }
            }

            if(action.getType() == PowerType.JESTER) {
                if(action.getEffectHandler().getChosenStudents1().size() > 3) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "Too many students were entered (max 3)", false)));
                    throw new InvalidAction("Power: Too many students were entered (max 3)");
                }
                if(action.getEffectHandler().getChosenStudents1().size() != action.getEffectHandler().getChosenStudents2().size()) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "Lists with different numbers of students", false)));
                    throw new InvalidAction("Power: Lists with different numbers of students");
                }
            }

            if(action.getType() == PowerType.MINSTREL) {
                if(action.getEffectHandler().getChosenStudents1().size() > 2) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "Too many students were entered (max 2)", false)));
                    throw new InvalidAction("Power: Too many students were entered (max 2)");
                }
                if(action.getEffectHandler().getChosenStudents1().size() != action.getEffectHandler().getChosenStudents2().size()) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "Lists with different numbers of students", false)));
                    throw new InvalidAction("Power: Lists with different numbers of students");
                }

                if(laneOverflowCheck(gameHandler,action)) {
                    Server.getInstance().getClientNetHandler(action.getPlayerId())
                            .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "At least one of the students entered cannot be added to the dining room", false)));
                    throw new InvalidAction("Power: At least one of the students entered cannot be added to the dining room");
                }
            }

            if (gameHandler.getGamePhase().equals(GamePhase.TURN)) {
                gameHandler.getGame().setEffectHandler(action.getEffectHandler());

                powerCard.power();
                System.out.println(powerCard.getType().toString().toLowerCase() + " power activated");

                //send changes to all players
                List<ClientNetworkHandler> broadcastClients = Server.getInstance().getConnectionsForGameBroadcast(gameHandler);
                for (ClientNetworkHandler broadcastClient : broadcastClients) {
                    broadcastClient.send(ActionHandler.toJson(action));
                }
            } else {
                Server.getInstance().getClientNetHandler(action.getPlayerId())
                        .send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.POWER, "invalid phase", false)));
                throw new InvalidAction("Power: invalid phase");
            }
            if (gameHandler.isEnded()) {
                notifyAndCloseGameEnd(gameHandler, null);
            }
        }
    }

    public static void notifyAndCloseGameEnd(GameHandler gameHandler, String playerIdToExclude) {
        synchronized (gameHandler) {
            Server.getInstance().getConnectionsForGameBroadcast(gameHandler).forEach(net -> {
                        if (net != null) {
                            List<String> leaderboard = gameHandler.getGame().getLeaderBoard().stream().map(Player::getName).filter(id -> !id.equals(playerIdToExclude)).collect(Collectors.toCollection(ArrayList::new));
                            if (playerIdToExclude != null)
                                leaderboard.add(playerIdToExclude); // Left the game player is in last position
                            net.send(
                                    ActionHandler.toJson(new GameInterruptedAction(null, leaderboard))
                            );
                            System.out.println(net + " has been notified GameInterruptedAction");
                        }
                    }
            );
            gameHandler.getOrderedTurnPlayers().stream().map(Player::getName).forEach(id -> Server.getInstance().removePlayerFromGame(id));

            if (gameHandler
                    .getOrderedTurnPlayers().stream().map(Player::getName).noneMatch(id -> Server.getInstance().getGameHandler(id) == gameHandler)) {   // All players disconnected from the game
                System.out.println(ConsoleColor.YELLOW + "All player disconnected from " + gameHandler + ". Game deleted.");
                Server.getInstance().removeGame(gameHandler);
            }
        }
    }

    /**
     * This method is used to check whether the dining room can overflow,
     * in which case it returns true.
     *
     * @param gameHandler The current game
     * @param action The action sent
     * @return True if the dining room overflows
     * @author Christian Confalonieri
     */
    private static boolean laneOverflowCheck(GameHandler gameHandler, Action action) {

        List<Student> studentsToDiningRoom = new ArrayList<>();

        if(action.getActionType() == ActionType.POWER ) {
            studentsToDiningRoom = ((PowerAction) action).getEffectHandler().getChosenStudents1();
        }
        else {
            if(action.getActionType() == ActionType.MOVESTUDENTS) {
                studentsToDiningRoom = ((MoveStudentsAction) action).getToDiningRoom();
            }
        }

        for(PawnColor color : PawnColor.values()) {
            int laneSize = gameHandler.getGame().getPlayerFromId(action.getPlayerId()).getSchool().getStudentsDiningRoom(color).size();
            int colorSize = 0;

            if(studentsToDiningRoom.stream().map(Pawn::getColor).toList().contains(color)) {
                for(Student student : studentsToDiningRoom) {
                    if(student.getColor() == color) {
                        colorSize++;
                    }
                }

                if((laneSize + colorSize) > 10) {
                    return true;
                }
            }
        }
        return false;

    }
}
