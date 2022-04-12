package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.MoveStudentsAction;
import it.polimi.ingsw.action.PlayCardAction;
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

    public static void moveStudents(MoveStudentsAction action) {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());

        if (gameHandler.getGamePhase().equals(GamePhase.TURN) &&
                gameHandler.getTurnPhase().equals(TurnPhase.MOVESTUDENTS)) {

            List<Student> studentsToDiningRoom = action.getToDiningRoom();
            if(studentsToDiningRoom != null) {
                for(Student student : studentsToDiningRoom) {
                    gameHandler.getCurrentPlayer().getSchool().entranceToDiningRoom(student);
                }
            }
            gameHandler.getGame().professorRelocate();

            Map<Student,Island> studentsToIsland = action.getToIsland();

            if(studentsToIsland != null) {
                for(Student student : studentsToIsland.keySet()) {
                    gameHandler.getCurrentPlayer().getSchool().entranceToIsland(student,studentsToIsland.get(student));
                }
            }

            gameHandler.advance();
        }
    }
}
