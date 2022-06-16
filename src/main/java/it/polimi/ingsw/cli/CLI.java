package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import java.util.*;

public class CLI {
    private Client client;

    private Thread inputHandlerThread;
    private Scanner inputScanner;
    private boolean shutdown;

    private CLIRenderHelper cliRenderHelper;

    protected CLI() {
        this.inputScanner = new Scanner(System.in).useDelimiter("\n");
        cliRenderHelper = new CLIRenderHelper();
        shutdown = false;
    }

    public static CLI CLIFactory() {
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
        System.out.println(ConsoleColor.GREEN_BRIGHT + "Command line interface started..." + ConsoleColor.RESET);
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


    public void printHelp(String[] command) {
        cliRenderHelper.printHelp(command);
    }

    /**
     * It simply calls the method with the same name as the InputCLI class as long as it is not invoked shutdown
     * @author Christian Confalonieri
     */
    public void inputHandler() {
        while(!shutdown) {
            InputCLI.inputHandler(inputScanner);
        }
        System.out.println(ConsoleColor.RED + "CLI InputHandler Closed" + ConsoleColor.RESET);
    }

    /**
     * For each client status it cleans the screen and prints the updated information
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
