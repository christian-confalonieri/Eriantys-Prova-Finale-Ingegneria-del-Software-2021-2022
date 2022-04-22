package it.polimi.ingsw.cli;

import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.game.GameHandler;

import java.util.List;

public class CLI {

    private GameHandler gameHandler;
    private String players;
    private String islandData;

    public CLI (GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printGameName () {
        System.out.println("ERYANTIS\n-----------------------");
    }

    public void printBag () {
        System.out.println("   ___\n" +
                "  /   \\\n" +
                " |__%__|\n" +
                " / : : \\\n" +
                "(       )\n" +
                " `-----'");
    }

    public void cliPlayers () {
        players =  "";
        for (int i = 0; i < gameHandler.getOrderedTurnPlayers().size(); i++) {
            if ( i != gameHandler.getOrderedTurnPlayers().size() - 1 ) {
                players += gameHandler.getOrderedTurnPlayers().get(i) + " Vs. ";
            } else {
                players += gameHandler.getOrderedTurnPlayers().get(i);
            }
        }
    }

    public void printPlayers () {
        System.out.println(players);
    }

    public void cliIslands () {
        String islandData = "";
        List<Student> students;
        List<Island> islands = gameHandler.getGame().getIslands();
        for ( int i = 0; i < islands.size(); i++ ) {
            islandData += "ISOLA " + ( i + 1 ) + "\n-----------------------\n";
            islandData += "Dimensione Isola: " + islands.get(i).getIslandSize() + "\n";
            islandData += "Colore Torre/i: " + islands.get(i).getTowerColor() + "\n";
            islandData += "Studenti: ";
            students = islands.get(i).getStudents();
            for ( int j = 0; j < students.size(); j++ ) {
                islandData += students.get(j).getColor() + " ";
            }
        }
    }

    public void printIslands () {
        System.out.println(islandData);
    }

}
