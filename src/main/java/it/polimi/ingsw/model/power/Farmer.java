package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.entity.School;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Professor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Farmer extends PowerCard {

    private Map<Player, Professor> movedProfessors;

    /**
     * @author Christian Confalonieri
     */
    public Farmer(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.FARMER);
        setCost(2);
    }

    // SHOULD BE ACTIVATED AFTER MOVING STUDENTS FROM THE ENTRANCE TO THE DINING ROOM.
    @Override
    public void power() {
        super.power();

        Player effectPlayer = getGameHandler().getGame().getEffectHandler().getEffectPlayer();
        School effectPlayerSchool = effectPlayer.getSchool();

        List<Player> players = new ArrayList<>(getGameHandler().getOrderedTurnPlayers());
        players.remove(effectPlayer);

        List<Professor> playerProfessors;
        School playerSchool;

        Map<Player, List<Professor>> chosenProfessors = new HashMap<>();

        for(Player player : players) {
            playerSchool = player.getSchool();
            playerProfessors = playerSchool.getProfessorTable();

            for(Professor professor: playerProfessors) {
                PawnColor color = professor.getColor();
                if(effectPlayerSchool.getStudentsDiningRoom(color).size() == playerSchool.getStudentsDiningRoom(color).size()) {
                    List<Professor> professors = chosenProfessors.get(player);
                    professors.add(professor);
                    chosenProfessors.put(player,professors);

                    playerSchool.removeProfessor(professor);
                    effectPlayerSchool.addProfessor(professor);
                }
            }
        }
        getGameHandler().getGame().getEffectHandler().setChosenProfessors(chosenProfessors);

    }

    @Override
    public void endPower() {
        super.endPower();

        Map<Player, List<Professor>> chosenProfessors = getGameHandler().getGame().getEffectHandler().getChosenProfessors();
        List<Professor> professors;
        Player effectPlayer = getGameHandler().getGame().getEffectHandler().getEffectPlayer();
        List<Player> players = new ArrayList<>();
        players.addAll(getGameHandler().getOrderedTurnPlayers());
        players.remove(effectPlayer);

        while(!chosenProfessors.isEmpty()){
            for(Player player : players) {
                if(chosenProfessors.containsKey(player)) {
                    professors = chosenProfessors.get(player);
                    for(Professor professor : professors) {
                        getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().removeProfessor(professor);
                        player.getSchool().addProfessor(professor);
                    }
                }
            }
        }
    }

}
