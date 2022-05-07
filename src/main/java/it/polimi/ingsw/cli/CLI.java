package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
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
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

public class CLI {
    private Client client;

    private Scanner inputScanner;
    private String players;
    private String islandData;
    private String cloudsData;
    private String schoolData;
    private String myCardsData;
    private String cardsData;
    private String gameEnded;

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
            students = clouds.get(i).pickAllStudents();
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

    // CAPIRE CHI E' IL GIOCATORE CORRENTE
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
            cardsData += "Card Number: " + players.get(i).getLastPlayedCard().getNumber();
            cardsData += "Number Of Movements: " + players.get(i).getLastPlayedCard().getMovements();
        }
    }

    private void printCards () {
        System.out.println(cardsData);
    }

    private void cliGameEnded () {
        gameEnded = "\n\n-----------------------\nMATCH IS OVER!\n-----------------------\n";
    }

    private void printGameEnded () {
        System.out.println(gameEnded);
    }
    /**
     * @author Christian Confalonieri
     */
    public void inputHandler() {
        while(true) {
            if (inputScanner.hasNext()) {
                String inputString = inputScanner.next();
                inputString = inputString.trim();

                String[] command = inputString.split(" ");

                switch(Client.getInstance().getClientState()) {
                    case LOGIN:
                        if(command.length != 1) {
                            System.out.println(ConsoleColor.RED + "Invalid login command" + ConsoleColor.RESET);
                            break;
                        }
                        LoginService.loginRequest(command[0]);
                        break;
                    case MAINMENU:
                        switch(command[0].toUpperCase()) {
                            case "LOGOUT":
                                LoginService.logoutRequest();
                                break;

                            case "NEWGAME":
                                if(command.length != 3) {
                                    System.out.println(ConsoleColor.RED + "Invalid new game command" + ConsoleColor.RESET);
                                    break;
                                }
                                try {
                                    if(Integer.parseInt(command[1]) < 2 || Integer.parseInt(command[1]) > 4) throw new NumberFormatException();
                                    LobbyService.newGameRequest(Integer.parseInt(command[1]), null, Wizard.valueOf(command[2].toUpperCase()));
                                } catch(NumberFormatException castingException) {
                                    System.out.println("Invalid number of player");
                                } catch (IllegalArgumentException wizardException) {
                                    System.out.println("Invalid wizard selected");
                                }
                                break;

                            case "JOINGAME":
                                if(command.length != 3) {
                                    System.out.println(ConsoleColor.RED + "Invalid join game command" + ConsoleColor.RESET);
                                    break;
                                }
                                try {
                                    if(Integer.parseInt(command[1]) < 2 || Integer.parseInt(command[1]) > 4) throw new NumberFormatException();
                                    LobbyService.joinGameRequest(Integer.parseInt(command[1]), Wizard.valueOf(command[2].toUpperCase()));
                                } catch(NumberFormatException castingException) {
                                    System.out.println("Invalid number of player");
                                } catch (IllegalArgumentException wizardException) {
                                    System.out.println("Invalid wizard selected");
                                }
                                break;

                            default:
                                System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                        }
                    break;
                    case WAITINGLOBBY:
                            switch (command[0].toUpperCase()) {
                                case "LOGOUT":
                                    if (command.length != 1) {
                                        System.out.println(ConsoleColor.RED + "Invalid logout command" + ConsoleColor.RESET);
                                        break;
                                    }
                                    LoginService.logoutRequest();
                                    break;
                                default:
                                    System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                            }
                        break;
                    case INGAME:
                        switch(Client.getInstance().getGameHandler().getGamePhase()) {
                            case PREPARATION:
                                if(command.length != 1) {
                                    System.out.println(ConsoleColor.RED + "Invalid play card command" + ConsoleColor.RESET);
                                    break;
                                }
                                else {
                                    if (command[0].equalsIgnoreCase("LOGOUT")) {
                                        LoginService.logoutRequest();
                                        break;
                                    }
                                }
                                GameService.playCardRequest
                                        (Client.getInstance().getGameHandler().getGame().getPlayerFromId
                                                (Client.getInstance().getPlayerId()).getHandCards().get(Integer.parseInt(command[0])-1));
                            break;
                            case TURN:
                                switch(Client.getInstance().getGameHandler().getTurnPhase()) {
                                    case MOVESTUDENTS:
                                        if(command.length != 6 && command.length != 1) {
                                            System.out.println(ConsoleColor.RED + "Invalid move students command" + ConsoleColor.RESET);
                                            break;
                                        }
                                        else {
                                            if (command.length == 1 && command[0].equalsIgnoreCase("LOGOUT")) {
                                                LoginService.logoutRequest();
                                                break;
                                            }
                                        }
                                        List<Student> toDiningRoom = new ArrayList<>();
                                        Map<Student,String> toIslands = new HashMap<>();

                                        for(int i=0; i<6; i+=2) {
                                            if(command[i].equalsIgnoreCase("D:")) {
                                                for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                                                    if(student.getColor().toString().equalsIgnoreCase(command[i+1]) && !toDiningRoom.contains(student) && !toIslands.keySet().contains(student)) {
                                                        toDiningRoom.add(student);
                                                        break;
                                                    }
                                                }
                                            }
                                            else {
                                                for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                                                    if(student.getColor().toString().equalsIgnoreCase(command[i+1]) && !toDiningRoom.contains(student) && !toIslands.keySet().contains(student)) {
                                                        toIslands.put(student,Client.getInstance().getGameHandler().getGame().getIslands().get(Integer.parseInt(command[i].substring(0, command[i].length()-1))-1).getUuid());
                                                        break;
                                                    }
                                                }
                                            }
                                        }


                                        GameService.moveStudentsRequest(toDiningRoom,toIslands);
                                        break;
                                    case MOVEMOTHER:
                                        if(command.length != 1) {
                                            System.out.println(ConsoleColor.RED + "Invalid move mother nature command" + ConsoleColor.RESET);
                                            break;
                                        }
                                            if(command[0].equalsIgnoreCase("LOGOUT")) {
                                                LoginService.logoutRequest();
                                            }
                                            else {
                                                GameService.moveMotherNatureRequest(Integer.parseInt(command[0]));
                                            }
                                        break;
                                    case MOVEFROMCLOUD:
                                        if(command.length != 1) {
                                            System.out.println(ConsoleColor.RED + "Invalid move from cloud command" + ConsoleColor.RESET);
                                            break;
                                        }
                                        if (command[0].equalsIgnoreCase("LOGOUT")) {
                                            LoginService.logoutRequest();
                                        }
                                        else {
                                            GameService.moveCloudRequest(Client.getInstance().getGameHandler().getGame().getClouds().get(Integer.parseInt(command[0])-1));
                                        }
                                        break;
                                    default:
                                        System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                                }
                            break;
                        }
                        break;
                    default:
                        System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                }
            }
        }

    }

    /**
     * @author Christian Confalonieri
     */
    public void render() {
        clearScreen();
        switch (client.getClientState()) {
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

                printGameName();
                // printPlayers();
                printIslands();
                printClouds();
                printSchool();
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
                        "where \"X\" is the number of players and \"COLOR\" is the color of the wizard \n");
            }
            case WAITINGLOBBY -> {
                System.out.println("WAITINGLOBBY");
                System.out.println("To disconnect, type: \"LOGOUT\".");
            }
        }
    }

}
