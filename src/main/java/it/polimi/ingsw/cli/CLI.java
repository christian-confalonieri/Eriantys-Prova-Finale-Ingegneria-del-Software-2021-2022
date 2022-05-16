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


    public boolean printHelp(String[] command) {
        cliRenderHelper.printHelp(command);
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
