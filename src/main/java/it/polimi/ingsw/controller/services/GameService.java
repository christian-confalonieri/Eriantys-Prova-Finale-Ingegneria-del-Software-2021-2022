package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.MoveMotherNatureAction;
import it.polimi.ingsw.action.MoveStudentsAction;
import it.polimi.ingsw.action.PlayCardAction;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.TurnPhase;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;

import java.util.List;
import java.util.Map;

public class GameService {
    public static void playCard(PlayCardAction action) {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());

        if (gameHandler.getGamePhase().equals(GamePhase.PREPARATION)) {
            if(gameHandler.previousPlayedCards().contains(action.getPlayedCard())) {
                if(gameHandler.previousPlayedCards().containsAll(gameHandler.getCurrentPlayer().getHandCards())) { // all your cards are already played: no alternative
                    gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());

                    // TODO send changes to all players
                }
                else { // invalid card (you can play other valid cards)
                    // TODO Ignore and communicate invalid card
                }
            }
            else { // valid card (nobody played this card in this turn)
                gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());

                // TODO send changes to all players
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
            Map<Student,Island> studentsToIsland = action.getToIsland();

            if(studentsToDiningRoom == null && studentsToIsland == null) {
                throw new InvalidAction("MoveStudentsAction: invalid lists");
            }

            if(studentsToDiningRoom.size() == 0 && studentsToIsland.size() == 0) {
                throw new InvalidAction("MoveStudentsAction: invalid lists");
            }

            if(studentsToIsland.size() + studentsToDiningRoom.size() !=
                    gameHandler.getGame().getGameRules().studentsRules.turnStudents) {
                throw new InvalidAction("MoveStudentsAction: invalid number of students");
            }


            if(!gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(studentsToDiningRoom) &&
            !gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(studentsToIsland.keySet())) {
                throw new InvalidAction("MoveStudentsAction: invalid students in the lists");
            }


            if(studentsToDiningRoom != null) {
                for(Student student : studentsToDiningRoom) {
                    gameHandler.getCurrentPlayer().getSchool().entranceToDiningRoom(student);
                }
            }
            gameHandler.getGame().professorRelocate();

            if(studentsToIsland != null) {
                for(Student student : studentsToIsland.keySet()) {
                    gameHandler.getCurrentPlayer().getSchool().entranceToIsland(student,studentsToIsland.get(student));
                }
            }
        }
        else {
            throw new InvalidAction("MoveStudentsAction: invalid phase");
        }
        gameHandler.advance();
        // TODO send changes to all players
    }

    /**
     * @author Christian Confalonieri
     */
    public static void moveMotherNature(MoveMotherNatureAction action) throws InvalidAction {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());

        if (gameHandler.getGamePhase().equals(GamePhase.TURN) &&
                gameHandler.getTurnPhase().equals(TurnPhase.MOVEMOTHER)) {

            if(action.getSteps() > 0 &&
                    action.getSteps() <= gameHandler.getCurrentPlayer().getLastPlayedCard().getMovements()) {

                gameHandler.getGame().getMotherNature().move(action.getSteps());
                Island currentIsland = gameHandler.getGame().getMotherNature().isOn();
                gameHandler.getGame().conquerIsland(currentIsland);

                if(currentIsland.checkUnifyNext()) {
                    gameHandler.getGame().unifyIsland(currentIsland,currentIsland.getNextIsland());
                }

                if(currentIsland.checkUnifyPrev()) {
                    gameHandler.getGame().unifyIsland(currentIsland,currentIsland.getPrevIsland());
                }
            }
            else {
                throw new InvalidAction("MoveMotherNature: invalid number of steps");
            }
        }
        else {
            throw new InvalidAction("MoveMotherNature: invalid phase");
        }
        gameHandler.advance();
        // TODO send changes to all players
    }
}
