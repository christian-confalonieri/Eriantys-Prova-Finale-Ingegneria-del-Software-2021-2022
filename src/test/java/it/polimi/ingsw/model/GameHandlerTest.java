package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Pawn;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.TurnPhase;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.server.PlayerLobby;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameHandlerTest {

    @Test
    void advance() {

    }

    @Test
    void twoPlayerMatchAdvance() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }


        GameHandler gameHandler = null;

        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);


        Game board = gameHandler.getGame();


        assertNotNull(board);

        assertEquals(board.getPowerCards().size(), 3);
        assertNotEquals(board.getPowerCards().get(0).getType(), board.getPowerCards().get(1).getType());
        assertNotEquals(board.getPowerCards().get(1).getType(), board.getPowerCards().get(2).getType());
        assertNotEquals(board.getPowerCards().get(0).getType(), board.getPowerCards().get(2).getType());

        assertEquals(board.getPowerCards().get(0).getGameHandler(), gameHandler);
        assertEquals(board.getPowerCards().get(1).getGameHandler(), gameHandler);
        assertEquals(board.getPowerCards().get(2).getGameHandler(), gameHandler);

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Pippo");
        gameHandler.getCurrentPlayer().playCard(Card.FOUR);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Topolino");
        gameHandler.getCurrentPlayer().playCard(Card.FIVE);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Pippo");
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVEMOTHER);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Pippo");
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVEFROMCLOUD);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Pippo");
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Topolino");
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVEMOTHER);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Topolino");
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVEFROMCLOUD);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Topolino");
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Pippo");
        gameHandler.getCurrentPlayer().playCard(Card.EIGHT);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Topolino");
        gameHandler.getCurrentPlayer().playCard(Card.THREE);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Topolino");
        gameHandler.advance();

        gameHandler.advance();
        gameHandler.advance();

        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Topolino");
        gameHandler.getCurrentPlayer().playCard(Card.THREE);
        gameHandler.advance();
    }

    @Test
    void threePlayerMatchAdvance() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Abbate", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Bertoldo", Wizard.GREEN));
        playerData.add(new PlayerLobby("Carnaroli", Wizard.PURPLE));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules3P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;


        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);


        Game board = gameHandler.getGame();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.getCurrentPlayer().playCard(Card.EIGHT);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Bertoldo");
        gameHandler.getCurrentPlayer().playCard(Card.FIVE);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.getCurrentPlayer().playCard(Card.NINE);
        gameHandler.advance();


        //In turno si procede in ordine di carta
        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Bertoldo");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();


        // In preparazione si prosegue in senso orario
        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Bertoldo");
        gameHandler.getCurrentPlayer().playCard(Card.THREE);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.getCurrentPlayer().playCard(Card.TWO);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.getCurrentPlayer().playCard(Card.TEN);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.advance();
        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVEMOTHER);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.advance();
        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVEFROMCLOUD);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.advance();
    }

    @Test
    void fourPlayerMatchAdvance() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Abbate", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Bertoldo", Wizard.GREEN));
        playerData.add(new PlayerLobby("Carnaroli", Wizard.PURPLE));
        playerData.add(new PlayerLobby("Drondo", Wizard.BLUE));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules4P.json"))); // First player is 1
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;


        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);


        Game board = gameHandler.getGame();

        assertEquals(board.getPowerCards().size(), 3);

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Bertoldo");
        gameHandler.getCurrentPlayer().playCard(Card.EIGHT);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.getCurrentPlayer().playCard(Card.FIVE);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Drondo");
        gameHandler.getCurrentPlayer().playCard(Card.NINE);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.getCurrentPlayer().playCard(Card.TEN);
        gameHandler.advance();


        //In turno si procede in ordine di carta
        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Bertoldo");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Drondo");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();


        // In preparazione si prosegue in senso orario
        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Carnaroli");
        gameHandler.getCurrentPlayer().playCard(Card.THREE);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Drondo");
        gameHandler.getCurrentPlayer().playCard(Card.TWO);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.getCurrentPlayer().playCard(Card.ONE);
        gameHandler.advance();

        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Bertoldo");
        gameHandler.getCurrentPlayer().playCard(Card.FOUR);
        gameHandler.advance();


        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.advance();
        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVEMOTHER);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.advance();
        assertEquals(gameHandler.getGamePhase(), GamePhase.TURN);
        assertEquals(gameHandler.getTurnPhase(), TurnPhase.MOVEFROMCLOUD);
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Abbate");
        gameHandler.advance();
    }

    @Test
    void turnSimulation() throws IOException, InvalidNewGameException, InvalidRulesException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));

        String rulesJson = null;

        rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));


        GameHandler gameHandler = null;


        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);
        assertNotNull(gameHandler);

        // PREPARATION
        // Pippo plays card PREPARATION
        gameHandler.getCurrentPlayer().playCard(Card.THREE);
        gameHandler.advance();


        // Topolino plays card PREPARATION
        gameHandler.getCurrentPlayer().playCard(Card.FIVE);
        gameHandler.advance();


        // Pippo moves 3 student MOVESTUDENTS
        assertEquals(gameHandler.getCurrentPlayer().getName(), "Pippo");
        Student s1 = gameHandler.getCurrentPlayer().getSchool().getEntrance().get(0);
        Student s2 = gameHandler.getCurrentPlayer().getSchool().getEntrance().get(1);
        Student s3 = gameHandler.getCurrentPlayer().getSchool().getEntrance().get(2);
        gameHandler.getCurrentPlayer().getSchool().entranceToDiningRoom(s1);
        gameHandler.getCurrentPlayer().getSchool().entranceToDiningRoom(s2);
        gameHandler.getCurrentPlayer().getSchool().entranceToIsland(s3, gameHandler.getGame().getIslands().get(1));
        assertEquals(gameHandler.getGame().getIslands().get(1).getStudents().size(), 2);


        gameHandler.getGame().professorRelocate(); // Pippo earnes professors
        assertTrue(gameHandler.getCurrentPlayer().getSchool().getProfessorTable().size() >= 1);
        gameHandler.advance();


        // Pippo moves mothernature MOVEMOTHER
        gameHandler.getGame().getMotherNature().move(1);

        // Call to conquerIsland(isOn)
        gameHandler.getGame().conquerIsland(gameHandler.getGame().getMotherNature().isOn());

        GameHandler finalGameHandler = gameHandler;
        if(gameHandler.getGame().getMotherNature().isOn().getStudents().stream().map(Pawn::getColor)
                .anyMatch(pawnColor -> finalGameHandler.getCurrentPlayer().getSchool().hasProfessor(pawnColor))) { // Pippo conquered the island!
            assertEquals(gameHandler.getGame().getMotherNature().isOn().getTowers().size(), 1);
        }
        else {
            assertEquals(gameHandler.getGame().getMotherNature().isOn().getTowers().size(), 0);
        }
        // Check unification (must not unificate)
        if (gameHandler.getGame().getMotherNature().isOn().checkUnifyNext())
            gameHandler.getGame().unifyIsland(gameHandler.getGame().getMotherNature().isOn(), gameHandler.getGame().getMotherNature().isOn().getNextIsland());
        if (gameHandler.getGame().getMotherNature().isOn().checkUnifyPrev())
            gameHandler.getGame().unifyIsland(gameHandler.getGame().getMotherNature().isOn(), gameHandler.getGame().getMotherNature().isOn().getPrevIsland());
        assertEquals(gameHandler.getGame().getIslands().size(), 12);

        gameHandler.advance();

        // Pippo choses an Cloud MOVECLOUD
        List<Student> pickedCloud = gameHandler.getGame().getClouds().get(0).pickAllStudents();
        for (Student student : pickedCloud)
            gameHandler.getCurrentPlayer().getSchool().addEntrance(student);

        gameHandler.advance();

        assertFalse(gameHandler.isEnded());
        List<Player> leaderBoard = gameHandler.getGame().getLeaderBoard();
        Player winner = leaderBoard.get(0);


        assertEquals(winner, gameHandler.getGame().getPlayers().get(0)); // Pippo must be the winner (it has most professors and maybe a tower)
    }
}