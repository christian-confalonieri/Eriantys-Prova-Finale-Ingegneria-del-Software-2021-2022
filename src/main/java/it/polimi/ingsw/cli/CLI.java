package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameHandler;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
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

        this.clearScreen();
        this.render();
    }

    private void clearScreen()
    {
        try
        {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }

    private void printGameName () {
        System.out.println("ERYANTIS\n-----------------------");
    }

    private void printBag () {
        System.out.println("   ___\n" +
                "  /   \\\n" +
                " |__%__|\n" +
                " / : : \\\n" +
                "(       )\n" +
                " `-----'");
    }

    private void cliPlayers () {
        players =  "";
        for (int i = 0; i < client.getGameHandler().getOrderedTurnPlayers().size(); i++) {
            if ( i != client.getGameHandler().getOrderedTurnPlayers().size() - 1 ) {
                players += client.getGameHandler().getOrderedTurnPlayers().get(i) + " Vs. ";
            } else {
                players += client.getGameHandler().getOrderedTurnPlayers().get(i);
            }
        }
    }

    private void printPlayers () {
        System.out.println(players);
    }

    private void cliIslands () {
        islandData = "";
        List<Student> students;
        List<Island> islands = client.getGameHandler().getGame().getIslands();
        for ( int i = 0; i < islands.size(); i++ ) {
            islandData += "\n\nISOLA " + ( i + 1 ) + "\n-----------------------\n";
            islandData += "Dimensione Isola: " + islands.get(i).getIslandSize() + "\n";
            if(islands.get(i).getTowerColor() != null)
                islandData += "Colore Torre/i: " + islands.get(i).getTowerColor() + "\n";
            islandData += "Studenti: ";
            students = islands.get(i).getStudents();
            for ( int j = 0; j < students.size(); j++ ) {
                islandData += ConsoleColor.PawnColorString(students.get(j).getColor()) + "o" + ConsoleColor.RESET;
            }
        }
    }

    private void printIslands () {
        System.out.println(islandData);
    }

    private void cliClouds () {
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

    private void printClouds () {
        System.out.println(cloudsData);
    }

    private void cliSchool () {
        List<Player> players = client.getGameHandler().getOrderedTurnPlayers();
        School school;
        List<Student> fila;
        for ( int i = 0; i < players.size(); i++ ) {
            school = players.get(i).getSchool();
            schoolData += "SCUOLA DI " + players.get(i).getName() + "\n-----------------------\n";
            schoolData += "INGRESSO:\n";
            for ( int j = 0; j < school.getEntrance().size(); j++ ) {
                schoolData += school.getEntrance().get(j).getColor() + " ";
            }
            schoolData += "\nPROFESSOR TABLE:\n";
            for ( int j = 0; j < school.getProfessorTable().size(); j++ ) {
                schoolData += school.getProfessorTable().get(j).getColor() + " ";
            }
            schoolData += "\nTORRI:\n";
            for ( int j = 0; j < school.getTowers().size(); j++ ) {
                schoolData += school.getTowers().get(j).getColor() + " ";
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

    private void printSchool () {
        System.out.println(schoolData);
    }

    public void inputHandler() {
        while(true) {
            if (inputScanner.hasNext()) {
                String inputString = inputScanner.next();
                inputString = inputString.trim();

                String[] command = inputString.split(" ");

                switch(command[0]) {
                    case "LOGIN":
                        if(command.length != 2) {
                            System.out.println(ConsoleColor.RED + "Invalid login command" + ConsoleColor.RESET);
                            break;
                        }
                        LoginService.loginRequest(command[1]);
                        break;

                    case "LOGOUT":
                    // case "LOGOUT\r": //TODO CHECK \r ERRORS
                        LoginService.logoutRequest();
                        break;

                    case "NEWGAME":
                        if(command.length != 3) {
                            System.out.println(ConsoleColor.RED + "Invalid new game command" + ConsoleColor.RESET);
                            break;
                        }
                        LobbyService.newGameRequest(Integer.parseInt(command[1]), null, Wizard.valueOf(command[2]));
                        break;

                    case "JOINGAME":
                        if(command.length != 3) {
                            System.out.println(ConsoleColor.RED + "Invalid join game command" + ConsoleColor.RESET);
                            break;
                        }
                        LobbyService.joinGameRequest(command[1], Wizard.valueOf(command[2]));
                        break;

                    default:
                        System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                }
            }
        }

    }

    public void render() {
        clearScreen();
        switch (client.getClientState()) {
            case LOGIN -> {
                printGameName();
                System.out.println("LOGIN");
                System.out.println("Enter your username: ");
            }
            case INGAME -> {
                cliIslands();
                cliClouds();
                cliSchool();
                cliPlayers();

                printGameName();
                // printPlayers();
                printIslands();
                printClouds();
                printSchool();
            }
            case MAINMENU -> {
                System.out.println("MAINMENU");
            }
            case WAITINGLOBBY -> {
                System.out.println("WAITINGLOBBY");
            }
        }
    }

}
