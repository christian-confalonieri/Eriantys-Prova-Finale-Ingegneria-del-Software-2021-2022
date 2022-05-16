package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.TurnPhase;
import it.polimi.ingsw.model.power.*;
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

    private CLIRenderHelper cliRenderHelper;

    private CLI() {
        this.inputScanner = new Scanner(System.in).useDelimiter("\n");
        cliRenderHelper = new CLIRenderHelper();
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
        shutdown = true;
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
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
        catch (Exception e)
        {
            // Rough, but works lol
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    }










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
        if(client.getGameHandler() != null) {
            for(int i=0; i<3; i++) {
                help += switch(client.getGameHandler().getGame().getPowerCards().get(i).getType()) {
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
                    "\nHowever, you cannot type the command to activate a character since you are not in the correct phase of the game.";
        }
        System.out.println(help + "\n");
        System.out.print("Enter the command: ");
    }

    /**
     * @author Christian Confalonieri
     */
    private void printMainMenuHelp() {
        System.out.println("\nTo disconnect, type the command: \"LOGOUT\".");
        System.out.println("Type the command: \"NEWGAME X COLOR\" or \"JOINGAME X COLOR\"\n" +
                "where \"X\" is the number of players and \"COLOR\" is the color of the wizard\n");
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
                "in which with \"D:\" we indicate the students to move to the dining room and with \"7:\" those to move to island 7");
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
        clearScreen();
        System.out.print(ConsoleColor.RESET);
        switch (client.getClientState()) {
            case LOADING -> {
                cliRenderHelper.printLoadingScreen();
            }
            case LOGIN -> {
                cliRenderHelper.printLoginScreen();
            }
            case INGAME -> {
                cliRenderHelper.printGameName();
                cliRenderHelper.printPowers();
                cliRenderHelper.printIslands();
                cliRenderHelper.printClouds();
                cliRenderHelper.printSchool();
                cliRenderHelper.printCards();
                cliRenderHelper.printMyCards();
                cliRenderHelper.printInputDialog();

            }
            case MAINMENU -> {
                cliRenderHelper.printMainMenu();
            }
            case WAITINGLOBBY -> {
                cliRenderHelper.printWaitingLobby();
            }
            case ENDGAME -> {
                cliRenderHelper.printGameEnded();
            }
        }
        System.out.print(": ");
        System.out.print(ConsoleColor.WHITE_BOLD_BRIGHT);
    }
}
