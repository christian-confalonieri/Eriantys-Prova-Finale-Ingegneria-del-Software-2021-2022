package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.*;
import it.polimi.ingsw.model.power.Friar;
import it.polimi.ingsw.model.power.Herbalist;
import it.polimi.ingsw.model.power.Jester;
import it.polimi.ingsw.model.power.Princess;
import it.polimi.ingsw.server.GameLobby;

import java.util.List;

public class CLIRenderHelper {

    private String loadingScreen;
    private String gameName;
    private String loginScreen;
    private String mainMenuScreen;
    private String lobbyScreen;

    private String players;
    private String islandData;
    private String cloudsData;
    private String schoolData;
    private String myCardsData;
    private String cardsData;
    private String gameEnded;

    public CLIRenderHelper() {
        loadingScreen = null;
        gameName = null;
        loginScreen = null;
        lobbyScreen = null;

        players = null;
        islandData = null;
        cloudsData = null;
        schoolData = null;
        myCardsData = null;
        cardsData = null;
        gameEnded = null;
    }

    /******************
    * TABS RENDERING *
    *******************/


    /**
     * Prints the game name
     */
    public void printGameName() {
        if(gameName == null)
            cliGameName();
        System.out.println(gameName);
    }

    private void cliGameName() {
        gameName = """
                 ___  ___  _ _  ___  _ _  ___  _  ___\s
                | __>| . \\| | || . || \\ ||_ _|| |/ __>
                | _> |   /\\   /|   ||   | | | | |\\__ \\
                |___>|_\\_\\ |_| |_|_||_\\_| |_| |_|<___/
                                                     \s""";
    }

    /**
     * Prints the loading screen
     */
    public void printLoadingScreen() {
        if(loadingScreen == null)
            cliLoadingScreen();
        System.out.println(loadingScreen);
    }

    private void cliLoadingScreen() {
        int STYLE = 0;
        switch (STYLE) {
            case 0:
                loadingScreen = ConsoleColor.PURPLE_BOLD_BRIGHT + "   ___" + ConsoleColor.RESET + "  ____   __ __   ____  ____   ______  ____ _____\n" +
                        ConsoleColor.PURPLE_BOLD_BRIGHT + "  /  _]" + ConsoleColor.RESET + "|    \\ |  |  | /    ||    \\ |      ||    / ___/\n" +
                        ConsoleColor.PURPLE_BOLD_BRIGHT + " /  [_ " + ConsoleColor.RESET + "|  D  )|  |  ||  o  ||  _  ||      | |  (   \\_ \n" +
                        ConsoleColor.PURPLE_BOLD_BRIGHT + "|    _]" + ConsoleColor.RESET + "|    / |  ~  ||     ||  |  ||_|  |_| |  |\\__  |\n" +
                        ConsoleColor.PURPLE_BOLD_BRIGHT + "|   [_ " + ConsoleColor.RESET + "|    \\ |___, ||  _  ||  |  |  |  |   |  |/  \\ |\n" +
                        ConsoleColor.PURPLE_BOLD_BRIGHT + "|     |" + ConsoleColor.RESET + "|  .  \\|     ||  |  ||  |  |  |  |   |  |\\    |\n" +
                        ConsoleColor.PURPLE_BOLD_BRIGHT + "|_____|" + ConsoleColor.RESET + "|__|\\_||____/ |__|__||__|__|  |__|  |____|\\___|";
                return;
            case 1:
                loadingScreen =  """
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
                return;
        }
    }

    /**
     * Prints the login screen
     */
    public void printLoginScreen() {
        if(loginScreen == null)
            cliLoginScreen();
        System.out.println(loginScreen);
    }

    private void cliLoginScreen() {
        loginScreen = """
                 _    ___  ___   _  _ _\s
                | |  | . |/  _> | || \\ |
                | |_ | | || <_/\\| ||   |
                |___|`___'`____/|_||_\\_|
                                       \s
                                       
                Username""";
    }

    /**
     * Prints main menu
     */
    public void printMainMenu() {
        cliMainMenu();
        System.out.println(mainMenuScreen);
    }
    private void cliMainMenu() {
        int count;
        mainMenuScreen = """
                 __ __  ___  _  _ _   __ __  ___  _ _  _ _\s
                |  \\  \\| . || || \\ | |  \\  \\| __>| \\ || | |
                |     ||   || ||   | |     || _> |   || ' |
                |_|_|_||_|_||_||_\\_| |_|_|_||___>|_\\_|`___'
                                                          \s
                                                          
                WAITING LOBBIES:
                """;
        if(Client.getInstance().getAllServerLobbys() != null) {
            count = 1;
            for (GameLobby lb : Client.getInstance().getAllServerLobbys()) {
                mainMenuScreen += " - " + lb.toString() + "[Lobby" + count + "]" + "\n";
                count++;
            }
        }
    }

    /**
     * Print game lobby waiting room
     */
    public void printWaitingLobby() {
        if(lobbyScreen == null)
            cliWaitingLobby();
        System.out.println(lobbyScreen);
    }

    private void cliWaitingLobby() {
        lobbyScreen = """
                 _    ___  ___  ___  _ _\s
                | |  | . || . >| . >| | |
                | |_ | | || . \\| . \\\\   /
                |___|`___'|___/|___/ |_|\s
                                        \s
                               
                Waiting for others players to join...""";
    }

    /******************
     * GAME RENDERING *
     *******************/

    /**
     * Print input description
     */
    public void printInputDialog() {
        if(Client.getInstance().getGameHandler().getCurrentPlayer().getName().equals(Client.getInstance().getPlayerId())) {
            switch(Client.getInstance().getGameHandler().getGamePhase()) {
                case PREPARATION:
                    System.out.println("Play a card");
                    break;
                case TURN:
                    switch (Client.getInstance().getGameHandler().getTurnPhase()) {
                        case MOVESTUDENTS -> System.out.println("Move the students" );
                        case MOVEMOTHER -> System.out.println("Move mother nature" );
                        case MOVEFROMCLOUD -> System.out.println("Pick students from cloud" );
                    }
                    break;
            }
        }
        else {
            System.out.println("Wait for your turn");
        }
    }

    /**
     * Print players informations
     */
    public void printPlayers () {
        cliPlayers();
        System.out.println(players);
    }

    private void cliPlayers () {
        players =  "";
        for (int i = 0; i < Client.getInstance().getGameHandler().getOrderedTurnPlayers().size(); i++) {
            if ( i != Client.getInstance().getGameHandler().getOrderedTurnPlayers().size() - 1 ) {
                players += Client.getInstance().getGameHandler().getOrderedTurnPlayers().get(i) + " Vs. ";
            } else {
                players += Client.getInstance().getGameHandler().getOrderedTurnPlayers().get(i);
            }
        }
    }


    /**
     * Print islands information
     */
    public void printIslands () {
        cliIslands();
        System.out.println(islandData);
    }

    private void cliIslands () {
        islandData = "";
        List<Student> students;
        List<Island> islands = Client.getInstance().getGameHandler().getGame().getIslands();
        MotherNature motherNature = Client.getInstance().getGameHandler().getGame().getMotherNature();
        Island islandMotherNature = motherNature.isOn();
        for ( int i = 0; i < islands.size(); i++ ) {
            if (islandMotherNature == islands.get(i)) {
                islandData += ConsoleColor.GREEN + "\n+-----------------<" + ConsoleColor.RESET + ConsoleColor.YELLOW + "M" + ConsoleColor.RESET + ConsoleColor.GREEN + ">-+" + ConsoleColor.RESET + "\nISLAND " + ( i + 1 ) + "\n-----------------------\n";
            } else {
                islandData += ConsoleColor.GREEN + "\n\n+---------------------+\n"+ ConsoleColor.RESET + "ISLAND " + ( i + 1 ) + "\n-----------------------\n";
            }
            //islandData += "Island Size: " + islands.get(i).getIslandSize() + "\n";
            if(islands.get(i).getTowerColor() != null) {
                for ( int j = 0; j < islands.get(i).getIslandSize(); j++ ) {
                    islandData += ConsoleColor.TowerColorString(islands.get(i).getTowerColor()) + "T " + ConsoleColor.RESET;
                }
                islandData += "\n-----------------------\n";
            }
            students = islands.get(i).getStudents();
            for ( int j = 0; j < students.size(); j++ ) {
                islandData += ConsoleColor.PawnColorString(students.get(j).getColor()) + "o " + ConsoleColor.RESET;
                if((j+1)%12 == 0) {
                    islandData += "\n";
                }
            }
            islandData += ConsoleColor.GREEN + "\n+---------------------+\n" + ConsoleColor.RESET;
        }
    }


    /**
     * Print clouds info
     */
    public void printClouds () {
        cliClouds();
        System.out.println(cloudsData);
    }

    private void cliClouds () {
        cloudsData = "";
        List<Cloud> clouds = Client.getInstance().getGameHandler().getGame().getClouds();
        List<Student> students;
        for ( int i = 0; i < clouds.size(); i++ ) {
            cloudsData += ConsoleColor.BLUE_BRIGHT + "\n+---------------------+\n" + ConsoleColor.RESET + "CLOUD " + ( i + 1 ) + "\n-----------------------\n";
            students = clouds.get(i).getStudents();
            for ( int j = 0; j < students.size(); j++ ) {
                cloudsData += ConsoleColor.PawnColorString(students.get(j).getColor()) + "o " + ConsoleColor.RESET;
            }
            cloudsData += ConsoleColor.BLUE_BRIGHT + "\n+---------------------+\n" + ConsoleColor.RESET;
            if(i+1!= clouds.size()) {
                cloudsData += "\n";
            }
        }
    }


    /**
     * Prints school informations
     */
    public void printSchool () {
        cliSchool();
        System.out.println(schoolData);
    }

    private void cliSchool () {
        schoolData = "";
        List<Player> players = Client.getInstance().getGameHandler().getOrderedTurnPlayers();
        School school;
        List<Student> fila;
        for ( int i = 0; i < players.size(); i++ ) {
            school = players.get(i).getSchool();
            schoolData += ConsoleColor.RED_BRIGHT + "\n+---------------<" + ConsoleColor.RESET + ConsoleColor.YELLOW + "©" + ":" + players.get(i).getCoins() + ConsoleColor.RESET + ConsoleColor.RED_BRIGHT + ">-+\n" + ConsoleColor.RESET + players.get(i).getName() + "' SCHOOL\n-------ENTRANCE--------\n";
            //schoolData += "ENTRANCE: ";
            for ( int j = 0; j < school.getEntrance().size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(school.getEntrance().get(j).getColor()) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\n------PROF.TABLE-------\n";
            //schoolData += "\nPROFESSOR TABLE: ";
            for ( int j = 0; j < school.getProfessorTable().size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(school.getProfessorTable().get(j).getColor()) + "p " + ConsoleColor.RESET;
            }
            schoolData += "\n--------TOWERS---------\n";
            //schoolData += "\nTOWERS: ";
            for ( int j = 0; j < school.getTowers().size(); j++ ) {
                schoolData += ConsoleColor.TowerColorString(school.getTowers().get(j).getColor()) + "T " + ConsoleColor.RESET;
            }
            schoolData += "\n------DINING-ROOM------";
            schoolData += "\n" + ConsoleColor.PawnColorString(PawnColor.YELLOW) + "Y: " + ConsoleColor.RESET;
            fila = school.getStudentsDiningRoom(PawnColor.YELLOW);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.YELLOW) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\n" + ConsoleColor.PawnColorString(PawnColor.RED) + "R: " + ConsoleColor.RESET;
            fila = school.getStudentsDiningRoom(PawnColor.RED);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.RED) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\n" + ConsoleColor.PawnColorString(PawnColor.GREEN) + "G: " + ConsoleColor.RESET;
            fila = school.getStudentsDiningRoom(PawnColor.GREEN);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.GREEN) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\n" + ConsoleColor.PawnColorString(PawnColor.BLUE) + "B: " + ConsoleColor.RESET;
            fila = school.getStudentsDiningRoom(PawnColor.BLUE);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.BLUE) + "o " + ConsoleColor.RESET;
            }
            schoolData += "\n" + ConsoleColor.PawnColorString(PawnColor.PINK) + "P: " + ConsoleColor.RESET;
            fila = school.getStudentsDiningRoom(PawnColor.PINK);
            for ( int j = 0; j < fila.size(); j++ ) {
                schoolData += ConsoleColor.PawnColorString(PawnColor.PINK) + "o " + ConsoleColor.RESET;
            }
            schoolData += ConsoleColor.RED_BRIGHT +"\n+---------------------+\n" + ConsoleColor.RESET;
            if(i+1!= players.size()) {
                schoolData += "\n";
            }
        }
    }


    /**
     * Prints the client's cards hand deck
     */
    public void printMyCards () {
        cliMyCards();
        System.out.println(myCardsData);
    }

    private void cliMyCards () {
        myCardsData = "";
        Player player = Client.getInstance().getGameHandler().getOrderedTurnPlayers().stream().filter(p -> p.getName().equals(Client.getInstance().getPlayerId())).findAny().get();
        List<Card> cards = player.getHandCards();
        myCardsData += ConsoleColor.YELLOW + "\n+---------------------+\n" + ConsoleColor.RESET + "YOUR CARDS:\n-----------------------\n";
        for ( int i = 0; i < cards.size(); i++ ) {
            //myCardsData += "Card Number: " + cards.get(i).getNumber() + "\n";
            //myCardsData += "Number Of Movements: " + cards.get(i).getMovements() + "\n\n";
            myCardsData += "(" + cards.get(i).getNumber() + ", " + cards.get(i).getMovements() + ") ";
            if((i+1)%3 == 0) {
                myCardsData += "\n";
            }
        }
        myCardsData += ConsoleColor.YELLOW + "\n+---------------------+\n" + ConsoleColor.RESET;
    }


    /**
     * Prints all the players last used card
     */
    public void printCards () {
        cliCards();
        System.out.println(cardsData);
    }

    private void cliCards () {
        cardsData = "";
        List<Player> players = Client.getInstance().getGameHandler().getOrderedTurnPlayers();
        for (int i = 0; i < players.size(); i++) {
            cardsData += ConsoleColor.YELLOW + "\n+---------------------+\n" + ConsoleColor.RESET + players.get(i).getName() + "' last played card:\n-----------------------\n";
            if(players.get(i).getLastPlayedCard() != null) {
                //cardsData += "Card Number: " + players.get(i).getLastPlayedCard().getNumber();
                //cardsData += "Number Of Movements: " + players.get(i).getLastPlayedCard().getMovements();
                cardsData += "(" + players.get(i).getLastPlayedCard().getNumber() + ", " + players.get(i).getLastPlayedCard().getMovements() + ") ";
            }
            cardsData += ConsoleColor.YELLOW + "\n+---------------------+\n" + ConsoleColor.RESET;
            if(i+1!= players.size()) {
                cardsData += "\n";
            }
        }
    }


    /**
     * Print the game ended screen with the leaderboard
     */
    public void printGameEnded () {
        cliGameEnded();
        System.out.println(gameEnded);
    }

    private void cliGameEnded() {
        gameEnded = "";
        List<String> leaderBoard = Client.getInstance().getFinalLeaderBoard() ==
                null ? Client.getInstance().getGameHandler().getGame().getLeaderBoard().stream().map(Player::getName).toList() : Client.getInstance().getFinalLeaderBoard();

        gameEnded += ConsoleColor.YELLOW + "\n+---------------------+\n" + ConsoleColor.RESET + "MATCH IS OVER!\n-----------------------\n";
        for ( int i = 0; i < leaderBoard.size(); i++ ) {
            gameEnded += ( i + 1 ) + "° " + leaderBoard.get(i) + "\n";
        }
        gameEnded += ConsoleColor.YELLOW + "+---------------------+\n" + ConsoleColor.RESET;
    }

    /**
     * Print the powers in the game
     * @author Christian Confalonieri
     */
    public void printPowers() {
        List<Student> students;
        System.out.println("\n" + ConsoleColor.RED + "+---------------------+\n" + ConsoleColor.RESET + "CHARACTERS: " + "\n-----------------------");
        for(int i=0; i<3; i++) {
            switch(Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getType()) {
                case FRIAR:
                    students = ((Friar)Client.getInstance().getGameHandler().getGame().getPowerCards().get(i)).getStudents();
                    System.out.print(Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getType() + " (" + Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getCost() + ")" + ": ");
                    for (int j = 0; j < students.size(); j++ ) {
                        System.out.print(ConsoleColor.PawnColorString(students.get(j).getColor()) + "o " + ConsoleColor.RESET);
                    }
                    System.out.println();
                    break;
                case JESTER:
                    students = ((Jester)Client.getInstance().getGameHandler().getGame().getPowerCards().get(i)).getStudents();
                    System.out.print(Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getType() + " (" + Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getCost() + ")" + ": ");
                    for (int j = 0; j < students.size(); j++ ) {
                        System.out.print(ConsoleColor.PawnColorString(students.get(j).getColor()) + "o " + ConsoleColor.RESET);
                    }
                    System.out.println();
                    break;
                case PRINCESS:
                    students = ((Princess)Client.getInstance().getGameHandler().getGame().getPowerCards().get(i)).getStudents();
                    System.out.print(Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getType() + " (" + Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getCost() + ")" + ": ");
                    for (int j = 0; j < students.size(); j++ ) {
                        System.out.print(ConsoleColor.PawnColorString(students.get(j).getColor()) + "o " + ConsoleColor.RESET);
                    }
                    System.out.println();
                    break;
                case HERBALIST:
                    System.out.print(Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getType() + " (" + Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getCost() + ")" + ": " +
                            ((Herbalist)Client.getInstance().getGameHandler().getGame().getPowerCards().get(i)).getNoEntryCards() + " No Entry tiles");
                    System.out.println();
                    break;
                default:
                    System.out.println(Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getType() + " (" + Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getCost() + ")");
                    break;
            }
        }
        System.out.println(ConsoleColor.RED + "+---------------------+\n" + ConsoleColor.RESET);
    }

    /******************
     * HELP RENDERING *
     ******************/


    /**
     * @author Christian Confalonieri
     */
    private void printPowersHelp() {
        String help = "";
        String friar = "\nTo activate the power of \"FRIAR\" type the command:\n" +
                "\"CHARACTER FRIAR RED 7\", where \"RED\" is the color of a FRIAR student and \"7\" is the number of an island of your choice.";
        String farmer = "\nTo activate the power of \"FARMER\" type the command:\n" +
                "\"CHARACTER FARMER\"";
        String herald = "\nTo activate the power of \"HERALD\" type the command:\n" +
                "\"CHARACTER HERALD 7\", where \"7\" is the number of an island of your choice.";
        String mailman = "\nTo activate the power of \"MAILMAN\" type the command:\n" +
                "\"CHARACTER MAILMAN\"";
        String herbalist = "\nTo activate the power of \"HERBALIST\" type the command:\n" +
                "\"CHARACTER HERBALIST 7\", where \"7\" is the number of an island of your choice.";
        String centaur = "\nTo activate the power of \"CENTAUR\" type the command:\n" +
                "\"CHARACTER CENTAUR\"";
        String jester = "\nTo activate the power of \"JESTER\" type the command:\n" +
                "\"CHARACTER JESTER E: RED E: BLUE E: GREEN J: RED J: BLUE J: GREEN\",\n where \"E: RED\" is, " +
                "for example, the color of a student in your entry\n and \"J: BLUE\" is the color of a student placed " +
                "on the jester (you can type students in your preferred order).";
        String knight = "\nTo activate the power of \"KNIGHT\" type the command:\n" +
                "\"CHARACTER KNIGHT\"";
        String harvester = "\nTo activate the power of \"HARVESTER\" type the command:\n" +
                "\"CHARACTER HARVESTER RED\", where \"RED\" is the color of a student of your choice.";
        String minstrel = "\nTo activate the power of \"MINSTREL\" type the command:\n" +
                "\"CHARACTER MINSTREL E: RED E: BLUE E: GREEN D: RED D: BLUE D: GREEN\",\n where \"E: RED\" is, " +
                "for example, the color of a student in your entry\n and \"D: BLUE\" is he color of a student in your dining room " +
                "(you can type students in your preferred order).";
        String princess = "\nTo activate the power of \"PRINCESS\" type the command:\n" +
                "\"CHARACTER PRINCESS RED\", where \"RED\" is the color of a student of your choice.";
        String thief = "\nTo activate the power of \"THIEF\" type the command:\n" +
                "\"CHARACTER THIEF RED\", where \"RED\" is the color of a student of your choice.";
        if(Client.getInstance().getGameHandler() != null) {
            for(int i=0; i<3; i++) {
                help += switch(Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getType()) {
                    case FRIAR -> friar;
                    case FARMER -> farmer;
                    case HERALD -> herald;
                    case MAILMAN -> mailman;
                    case HERBALIST -> herbalist;
                    case CENTAUR -> centaur;
                    case JESTER -> jester;
                    case KNIGHT -> knight;
                    case HARVESTER -> harvester;
                    case MINSTREL -> minstrel;
                    case PRINCESS -> princess;
                    case THIEF -> thief;
                };
            }
        }
        else {
            help = friar + farmer + herald + mailman + herbalist + centaur + jester + knight + harvester + minstrel + princess + thief +
                    "\n\nHowever, you cannot type the command to activate a character since you are not in the correct phase of the game.";
        }
        System.out.println(help + "\n");
        System.out.print("Enter the command: ");
    }

    /**
     * @author Christian Confalonieri
     */
    private void printMainMenuHelp() {
        System.out.println("\nTo disconnect, type the command: \"LOGOUT\".");
        System.out.println("Type the command: \"NEWGAME X COLOR\" or \"JOINGAME X COLOR\" or\"JOINGAMEID Y COLOR\"\n" +
                "where \"X\" is the number of players, Y is the number of the chosen lobby and \"COLOR\" is the color of the wizard\n");
        if(Client.getInstance().getClientState() != ClientState.MAINMENU) {
            System.out.println("However, you cannot type the main menu command since you are not in the correct phase of the game.\n");
        }
        System.out.print("Enter the command: ");
    }

    /**
     * @author Christian Confalonieri
     */
    private void printWaitingLobbyHelp() {
        System.out.println("\nTo disconnect, type: \"LOGOUT\".\n");
        System.out.print("Enter the command: ");
    }

    /**
     * @author Christian Confalonieri
     */
    private void printMoveStudentsHelp() {
        System.out.println("\nType the command: \"D: YELLOW D: BLUE 7: GREEN\"\n" +
                "in which with \"D:\" we indicate the students to move to the dining room and with \"7:\" those to move to island 7\n");
        if(!(Client.getInstance().getClientState() == ClientState.INGAME && Client.getInstance().getGameHandler().getGamePhase() == GamePhase.TURN && Client.getInstance().getGameHandler().getTurnPhase() == TurnPhase.MOVESTUDENTS)) {
            System.out.println("However, you cannot type the command to move students since you are not in the correct phase of the game.");
        }
        System.out.print("\nEnter the command: ");
    }

    /**
     * @author Christian Confalonieri
     */
    public boolean printHelp(String[] command) {
        if(command.length != 2) {
            System.out.println(ConsoleColor.RED + "Invalid help command" + ConsoleColor.RESET);
            return true;
        }
        switch(command[1].toUpperCase()) {
            case "HELP":
                System.out.println("\n\"HELP CHARACTERS\"\n"+
                        "\n\"HELP MAINMENU\"\n" +
                        "\n\"HELP WAITINGLOBBY\"\n" +
                        "\n\"HELP MOVESTUDENTS\"\n");
                System.out.print("Enter the command: ");
                break;
            case "CHARACTERS":
                printPowersHelp();
                break;
            case "MAINMENU":
                printMainMenuHelp();
                break;
            case "WAITINGLOBBY":
                printWaitingLobbyHelp();
                break;
            case "MOVESTUDENTS":
                printMoveStudentsHelp();
                break;
            default:
                System.out.println(ConsoleColor.RED + "Invalid help command" + ConsoleColor.RESET);
                break;
        }
        return true;
    }
}
