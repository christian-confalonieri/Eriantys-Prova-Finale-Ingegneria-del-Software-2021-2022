package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.Server;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

public class CLI {
    public static CLI getInstance() {
        if(singleton == null)
            singleton = CLIFactory();
        return singleton;
    }
    private static CLI singleton;

    private Client client;

    private Thread inputHandlerThread;
    private Scanner inputScanner;
    private boolean shutdown;

    private String players;
    private String islandData;
    private String cloudsData;
    private String schoolData;
    private String myCardsData;
    private String cardsData;
    private String gameEnded;

    private CLI() {
        this.inputScanner = new Scanner(System.in).useDelimiter("\n");

        shutdown = false;
    }

    private static CLI CLIFactory() {
        CLI newCli = new CLI();
        newCli.inputHandlerThread = new Thread(newCli::inputHandler);
        return newCli;
    }

    public void attach(Client client) {
        this.client = client;
    }

    public void start() {
        inputHandlerThread.start();
        render();
    }

    public void shutdown() {
        shutdown = true; // TODO no
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

    private void printLoadingScreen() {
        System.out.println(cliLoadingScreen());
    }

    private String cliLoadingScreen() {
        int STYLE = 0;
        switch (STYLE) {
            case 0:
                return ConsoleColor.PURPLE_BOLD_BRIGHT + "   ___" + ConsoleColor.RESET + "  ____   __ __   ____  ____   ______  ____ _____\n" +
                       ConsoleColor.PURPLE_BOLD_BRIGHT + "  /  _]" + ConsoleColor.RESET + "|    \\ |  |  | /    ||    \\ |      ||    / ___/\n" +
                       ConsoleColor.PURPLE_BOLD_BRIGHT + " /  [_ " + ConsoleColor.RESET + "|  D  )|  |  ||  o  ||  _  ||      | |  (   \\_ \n" +
                       ConsoleColor.PURPLE_BOLD_BRIGHT + "|    _]" + ConsoleColor.RESET + "|    / |  ~  ||     ||  |  ||_|  |_| |  |\\__  |\n" +
                       ConsoleColor.PURPLE_BOLD_BRIGHT + "|   [_ " + ConsoleColor.RESET + "|    \\ |___, ||  _  ||  |  |  |  |   |  |/  \\ |\n" +
                       ConsoleColor.PURPLE_BOLD_BRIGHT + "|     |" + ConsoleColor.RESET + "|  .  \\|     ||  |  ||  |  |  |  |   |  |\\    |\n" +
                       ConsoleColor.PURPLE_BOLD_BRIGHT + "|_____|" + ConsoleColor.RESET + "|__|\\_||____/ |__|__||__|__|  |__|  |____|\\___|";
            case 1:
                return """
                                                                                                         \s
                                                                                                         \s
                              __.....__                                    _..._            .--.         \s
                          .-''         '.         .-.          .-        .'     '.          |__|         \s
                         /     .-''"'-.  `. .-,.--.\\ \\        / /       .   .-.   .     .|  .--.         \s
                        /     /________\\   \\|  .-. |\\ \\      / /  __    |  '   '  |   .' |_ |  |         \s
                        |                  || |  | | \\ \\    / /.:--.'.  |  |   |  | .'     ||  |     _   \s
                        \\    .-------------'| |  | |  \\ \\  / // |   \\ | |  |   |  |'--.  .-'|  |   .' |  \s
                         \\    '-.____...---.| |  '-    \\ `  / `" __ | | |  |   |  |   |  |  |  |  .   | /\s
                          `.             .' | |         \\  /   .'.''| | |  |   |  |   |  |  |__|.'.'| |//\s
                            `''-...... -'   | |         / /   / /   | |_|  |   |  |   |  '.'  .'.'.-'  / \s
                                            |_|     |`-' /    \\ \\._,\\ '/|  |   |  |   |   /   .'   \\_.'  \s
                                                     '..'      `--'  `" '--'   '--'   `'-'               \s""".indent(1);
        }
        return "";
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
            islandData += "\n\nISLAND " + ( i + 1 ) + "\n-----------------------\n";
            islandData += "Island Size: " + islands.get(i).getIslandSize() + "\n";
            if(islands.get(i).getTowerColor() != null)
                islandData += "Tower(s) Color: " + islands.get(i).getTowerColor() + "\n";
            islandData += "Students: ";
            students = islands.get(i).getStudents();
            for ( int j = 0; j < students.size(); j++ ) {
                islandData += ConsoleColor.PawnColorString(students.get(j).getColor()) + "o " + ConsoleColor.RESET;
            }
        }
    }

    private void printIslands () {
        System.out.println(islandData);
    }

    private void cliClouds () {
        cloudsData = "";
        List<Cloud> clouds = client.getGameHandler().getGame().getClouds();
        List<Student> students;
        for ( int i = 0; i < clouds.size(); i++ ) {
            cloudsData += "\n\nCLOUD " + ( i + 1 ) + "\n-----------------------\n";
            cloudsData += "Students: ";
            students = clouds.get(i).getStudents();
            for ( int j = 0; j < students.size(); j++ ) {
                cloudsData += ConsoleColor.PawnColorString(students.get(j).getColor()) + "o " + ConsoleColor.RESET;
            }
        }
    }

    private void printClouds () {
        System.out.println(cloudsData);
    }

    private void cliSchool () {
        schoolData = "";
        List<Player> players = client.getGameHandler().getOrderedTurnPlayers();
        School school;
        List<Student> fila;
        for ( int i = 0; i < players.size(); i++ ) {
            school = players.get(i).getSchool();
            schoolData += "\n\n" + players.get(i).getName() + "' SCHOOL\n-----------------------\n";
            schoolData += "ENTRANCE: ";
            for ( int j = 0; j < school.getEntrance().size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(school.getEntrance().get(j).getColor()) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\nPROFESSOR TABLE: ";
            for ( int j = 0; j < school.getProfessorTable().size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(school.getProfessorTable().get(j).getColor()) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\nTOWERS: ";
            for ( int j = 0; j < school.getTowers().size(); j++ ) {
                schoolData += ConsoleColor.TowerColorString(school.getTowers().get(j).getColor()) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\nDINING ROOM:";
            schoolData += "\nYELLOW: ";
            fila = school.getStudentsDiningRoom(PawnColor.YELLOW);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.YELLOW) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\nRED: ";
            fila = school.getStudentsDiningRoom(PawnColor.RED);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.RED) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\nGREEN: ";
            fila = school.getStudentsDiningRoom(PawnColor.GREEN);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.GREEN) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\nBLUE: ";
            fila = school.getStudentsDiningRoom(PawnColor.BLUE);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.BLUE) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\nPINK: ";
            fila = school.getStudentsDiningRoom(PawnColor.PINK);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.PINK) + "o " + ConsoleColor.RESET;
            }
        }
    }

    private void printSchool () {
        System.out.println(schoolData);
    }

    private void cliMyCards () {
        myCardsData = "";
        Player player = client.getGameHandler().getOrderedTurnPlayers().stream().filter(p -> p.getName().equals(client.getPlayerId())).findAny().get();
        List<Card> cards = player.getHandCards();
        myCardsData += "\n\nYOUR CARDS:\n-----------------------\n";
        for ( int i = 0; i < cards.size(); i++ ) {
            myCardsData += "Card Number: " + cards.get(i).getNumber() + "\n";
            myCardsData += "Number Of Movements: " + cards.get(i).getMovements() + "\n\n";
        }
    }

    private void printMyCards () {
        System.out.println(myCardsData);
    }

    private void cliCards () {
        cardsData = "";
        List<Player> players = client.getGameHandler().getOrderedTurnPlayers();
        for (int i = 0; i < players.size(); i++) {
            cardsData += "\n\n" + players.get(i).getName() + "' last played card:\n-----------------------\n";
            if(players.get(i).getLastPlayedCard() != null) {
                cardsData += "Card Number: " + players.get(i).getLastPlayedCard().getNumber();
                cardsData += "Number Of Movements: " + players.get(i).getLastPlayedCard().getMovements();
            }
        }
    }

    private void printCards () {
        System.out.println(cardsData);
    }

    private void cliGameEnded() {
        gameEnded = "";
        List<String> leaderBoard = client.getFinalLeaderBoard() ==
                null ? client.getGameHandler().getGame().getLeaderBoard().stream().map(Player::getName).toList() : client.getFinalLeaderBoard();

        gameEnded = "\n\n-----------------------\nMATCH IS OVER!\n-----------------------\n";
        for ( int i = 0; i < leaderBoard.size(); i++ ) {
            gameEnded += ( i + 1 ) + "° " + leaderBoard.get(i) + "\n";
        }
    }

    private void printGameEnded () {
        System.out.println(gameEnded);
    }
    /**
     * @author Christian Confalonieri
     */
    public void inputHandler() {
        while(!shutdown) {
            InputCLI.inputHandler(inputScanner);
        }
        System.out.println(ConsoleColor.RED + "CLI InputHandler Closed" + ConsoleColor.RESET);
    }

    /**
     * @author Christian Confalonieri
     */
    public void render() {
        //TODO try catch null client
        clearScreen();
        switch (client.getClientState()) {
            case LOADING -> {
                printLoadingScreen();
                System.out.println();
            }
            case LOGIN -> {
                printGameName();
                System.out.println("LOGIN");
                System.out.print("Enter your username: ");
            }
            case INGAME -> {
                cliIslands();
                cliClouds();
                cliSchool();
                cliPlayers();
                cliCards();
                cliMyCards();

                printGameName();
                // printPlayers();
                printIslands();
                printClouds();
                printSchool();
                printCards();
                printMyCards();
                System.out.println("To disconnect, type: \"LOGOUT\".");
                if(Client.getInstance().getGameHandler().getCurrentPlayer().getName().equals(Client.getInstance().getPlayerId())) {
                    switch(Client.getInstance().getGameHandler().getGamePhase()) {
                        case PREPARATION:
                            System.out.print("Play a card: ");
                            break;
                        case TURN:
                            switch (Client.getInstance().getGameHandler().getTurnPhase()) {
                                case MOVESTUDENTS:
                                    System.out.println("Type in the students to be moved:\n" +
                                            "For example, \"D: YELLOW D: BLUE 7: GREEN\"\n" +
                                            "in which with \"D:\" we indicate the students to move to the dining room and with \"7:\" those to move to island 7");
                                    break;
                                case MOVEMOTHER:
                                    System.out.print("Enter the number of movements that mother nature has to make: ");
                                    break;
                                case MOVEFROMCLOUD:
                                    System.out.print("Enter the number of the cloud through which you want to pick up students: ");
                                    break;
                            }
                            break;
                    }
                }
                else {
                    System.out.println("Waits for the other players to finish playing his turn");
                }
            }
            case MAINMENU -> {
                System.out.println("MAINMENU");
                System.out.println("To disconnect, type: \"LOGOUT\".");
                System.out.println("Type: \"NEWGAME X COLOR\" or \"JOINGAME X COLOR\"\n" +
                        "where \"X\" is the number of players and \"COLOR\" is the color of the wizard");
            }
            case WAITINGLOBBY -> {
                System.out.println("WAITINGLOBBY");
                System.out.println("To disconnect, type: \"LOGOUT\".");
            }
            case ENDGAME -> {
                cliGameEnded();
                printGameEnded();
            }
        }
    }

}
