package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameHandler;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private Client client;

    private Scanner inputScanner;
    private String players;
    private String islandData;
    private String cloudsData;
    private String schoolData;

    public CLI (Client client) {
        this.client = client;
        this.inputScanner = new Scanner(System.in).useDelimiter("\n");
        new Thread(this::inputHandler).start();
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
        for (int i = 0; i < client.getGameHandler().getOrderedTurnPlayers().size(); i++) {
            if ( i != client.getGameHandler().getOrderedTurnPlayers().size() - 1 ) {
                players += client.getGameHandler().getOrderedTurnPlayers().get(i) + " Vs. ";
            } else {
                players += client.getGameHandler().getOrderedTurnPlayers().get(i);
            }
        }
    }

    public void printPlayers () {
        System.out.println(players);
    }

    public void cliIslands () {
        islandData = "";
        List<Student> students;
        List<Island> islands = client.getGameHandler().getGame().getIslands();
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

    public void cliClouds () {
        List<Cloud> clouds = client.getGameHandler().getGame().getClouds();
        List<Student> students;
        for ( int i = 0; i < clouds.size(); i++ ) {
            cloudsData += "NUVOLA " + ( i + 1 ) + "\n-----------------------\n";
            cloudsData += "Studenti: ";
            students = clouds.get(i).pickAllStudents();
            for ( int j = 0; j < students.size(); j++ ) {
                cloudsData += students.get(j).getColor() + " ";
            }
        }
    }

    public void printClouds () {
        System.out.println(cloudsData);
    }

    public void cliSchool () {
        List<Player> players = client.getGameHandler().getGame().getPlayers();
        School school;
        List<Student> fila;
        for ( int i = 0; i < players.size(); i++ ) {
            school = players.get(i).getSchool();
            schoolData += "SCUOLA DI " + players.get(i).getName() + "\n-----------------------\n";
            schoolData += "INGRESSO:\n";
            for ( int j = 0; j < school.getEntrance().size(); j++ ) {
                schoolData += school.getEntrance().get(i).getColor() + " ";
            }
            schoolData += "\nPROFESSOR TABLE:\n";
            for ( int j = 0; j < school.getProfessorTable().size(); j++ ) {
                schoolData += school.getProfessorTable().get(i).getColor() + " ";
            }
            schoolData += "\nTORRI:\n";
            for ( int j = 0; j < school.getTowers().size(); j++ ) {
                schoolData += school.getTowers().get(i).getColor() + " ";
            }
            schoolData += "\nDINING ROOM:";
            schoolData += "\nGIALLO: ";
            fila = school.getStudentsDiningRoom(PawnColor.YELLOW);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += "O ";
            }
            schoolData += "\nROSSO: ";
            fila = school.getStudentsDiningRoom(PawnColor.RED);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += "O ";
            }
            schoolData += "\nVERDE: ";
            fila = school.getStudentsDiningRoom(PawnColor.GREEN);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += "O ";
            }
            schoolData += "\nBLU: ";
            fila = school.getStudentsDiningRoom(PawnColor.BLUE);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += "O ";
            }
            schoolData += "\nROSA: ";
            fila = school.getStudentsDiningRoom(PawnColor.PINK);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += "O ";
            }
        }
    }

    public void printSchool () {
        System.out.println(schoolData);
    }

    public void inputHandler() {
        while(true) {
            if (inputScanner.hasNext()) {
                String[] command = inputScanner.next().split(" ");
                switch(command[0]) {
                    case "LOGIN":
                        if(command.length < 2) {
                            System.out.println(ConsoleColor.RED + "Invalid login command" + ConsoleColor.RESET);
                            break;
                        }
                        LoginService.loginRequest(command[1]);
                        break;
                    default:
                        System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                }
            }
        }
    }

}
