package it.polimi.ingsw.client;

import it.polimi.ingsw.cli.CLI;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.controller.NetworkController;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.GameLobby;

import java.io.Console;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class Client implements Runnable {

    public static Client getInstance() {
        return singleton;
    }
    private static Client singleton;

    public final String serverIp;
    public final int serverPort;

    private CLI cli;
    private ClientState clientState;
    private GameHandler gameHandler;
    private List<GameLobby> allServerLobbys;
    private GameLobby gameLobby;

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

        clientState = ClientState.LOADING;
        gameHandler = null;
        clientController = new ClientController();
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
        System.out.println(ConsoleColor.GREEN_BRIGHT + "Command line interface started..." + ConsoleColor.RESET);
        networkController.start();
        System.out.println(ConsoleColor.GREEN_BRIGHT + "NetworkController connected..." + ConsoleColor.RESET);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            //
        }
        clientState = ClientState.LOGIN;
        cli.render();
    }

    private static String parseArg(String[] args, String option, String optionVerbose) {
        for (int i = 0; i < args.length - 1; i++) {
            String arg = args[i];
            if (arg.equalsIgnoreCase(option) || arg.equalsIgnoreCase(optionVerbose)) return args[i+1];
        }
        return null;
    }

    public static void main(String[] args) {
        String hostname = parseArg(args, "-a", "--address");
        String port = parseArg(args, "-p", "--port");

        try {
            singleton = new Client(hostname == null ? "localhost" : hostname, Integer.parseInt(port == null ? "23154" : port));
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColor.RED + "Invalid port number" + ConsoleColor.RESET);
            return;
        }
        CLI mainCLI = CLI.getInstance();
        mainCLI.attach(singleton);
        singleton.attachCLI(mainCLI);
        mainCLI.render();
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
}
