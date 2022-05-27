package it.polimi.ingsw.client;

import it.polimi.ingsw.cli.CLI;
import it.polimi.ingsw.cli.CLIBypass;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.controller.NetworkController;
import it.polimi.ingsw.gui.GUI;
import it.polimi.ingsw.gui.GUIBypass;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.GameLobby;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client implements Runnable {

    public static Client getInstance() {
        return singleton;
    }
    private static Client singleton;

    public final String serverIp;
    public final int serverPort;

    private CLI cli;
    private volatile GUI gui;

    private ClientState clientState;
    private GameHandler gameHandler;
    private List<String> finalLeaderBoard;
    private List<GameLobby> allServerLobbys;
    private GameLobby gameLobby;
    private boolean pollAllLobbies;



    private NetworkController networkController;
    private final ClientController clientController;
    private String playerId;


    public void restartNetwork(String serverIp, int serverPort) {
        clientState = ClientState.LOADING;
        cli.render();

        if(Client.getInstance().getNetworkController() != null) Client.getInstance().getNetworkController().shutdown();
        attachNetwork();
        networkController.start();

        clientState = ClientState.LOGIN;
        cli.render();
    }

    public Client(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.pollAllLobbies = true;

        clientState = ClientState.LOADING;
        gameHandler = null;
        clientController = new ClientController();
    }

    public void attachGUI() {
        new Thread(() -> GUI.launch(GUI.class)).start();
    }

    public void attachCLI(CLI cli) {
        this.cli = cli;
    }

    public void attachNetwork() {
        boolean connected = false;
        while (!connected){
            try {
                Socket socket = new Socket(serverIp, serverPort);
                networkController = NetworkController.networkControllerFactory(socket);
                connected = true;

            } catch (UnknownHostException e) {
                System.out.println(ConsoleColor.RED + "Unknown server address" + ConsoleColor.RESET);
                return;
            } catch (IOException e) {
                System.out.println(ConsoleColor.RED + "Failed to connect to the server. Retrying in 10s" + ConsoleColor.RESET);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    //
                }
            }
        }
    }

    @Override
    public void run() {
        cli.start();
        networkController.start();
        System.out.println(ConsoleColor.GREEN_BRIGHT + "NetworkController connected..." + ConsoleColor.RESET);

        while (gui == null) {
            Thread.onSpinWait();
        }
        gui.guiCallLoading(guiLoadingController -> guiLoadingController.setConnectionLogLabel("Successfully connected to the server"));

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            //
        }
        clientState = ClientState.LOGIN;
        cli.render();
        gui.notifyStateChange();

    }

    private static String parseArg(String[] args, String option, String optionVerbose) {
        for (int i = 0; i < args.length - 1; i++) {
            String arg = args[i];
            if (arg.equalsIgnoreCase(option) || arg.equalsIgnoreCase(optionVerbose)) return args[i+1];
        }
        return null;
    }

    private static LaunchMode parseLaunchMode(String[] args) {
        for(String s : args) {
            if(s.contains("cli")) return LaunchMode.CLI;
            if(s.contains("gui")) return LaunchMode.GUI;
            if(s.contains("debug")) return LaunchMode.DEBUG;
        }
        return LaunchMode.GUI; // Standard is GUI
    }

    private enum LaunchMode {
        CLI, GUI, DEBUG
    }

    public static void main(String[] args) {
        String hostname = parseArg(args, "-a", "--address");
        String port = parseArg(args, "-p", "--port");
        LaunchMode launchMode = parseLaunchMode(args);

        try {
            singleton = new Client(hostname == null ? "localhost" : hostname, Integer.parseInt(port == null ? "23154" : port));
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColor.RED + "Invalid port number" + ConsoleColor.RESET);
            return;
        }

        if(launchMode == LaunchMode.CLI || launchMode == LaunchMode.DEBUG)
            singleton.attachCLI(CLI.CLIFactory());
        else
            singleton.attachCLI(new CLIBypass());

        singleton.cli.attach(singleton); // For func written with client and not Client.getInstance()
        singleton.cli.render();

        if (launchMode == LaunchMode.GUI || launchMode == LaunchMode.DEBUG)
            singleton.attachGUI();
        else
            singleton.gui = new GUIBypass();

        singleton.attachNetwork();
        singleton.run();
    }


    public NetworkController getNetworkController() {
        return networkController;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public List<GameLobby> getAllServerLobbys() {
        return allServerLobbys;
    }

    public void setAllServerLobbys(List<GameLobby> allServerLobbys) {
        this.allServerLobbys = allServerLobbys;
    }

    public GameLobby getGameLobby() {
        return gameLobby;
    }

    public void setGameLobby(GameLobby gameLobby) {
        this.gameLobby = gameLobby;
    }

    public CLI getCli() {
        return cli;
    }

    public List<String> getFinalLeaderBoard() {
        return finalLeaderBoard;
    }

    public void setFinalLeaderBoard(List<String> finalLeaderBoard) {
        this.finalLeaderBoard = finalLeaderBoard;
    }

    public boolean isPollAllLobbies() {
        return pollAllLobbies;
    }

    public void setPollAllLobbies(boolean pollAllLobbies) {
        this.pollAllLobbies = pollAllLobbies;
    }

    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
