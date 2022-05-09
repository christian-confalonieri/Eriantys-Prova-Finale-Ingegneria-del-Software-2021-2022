package it.polimi.ingsw.client;

import it.polimi.ingsw.cli.CLI;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.controller.NetworkController;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.GameLobby;

import java.io.IOException;
import java.net.Socket;
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

    private final NetworkController networkController;
    private final ClientController clientController;
    private String playerId;

    public static void restart(String serverIp, int serverPort) {
        if(Client.getInstance() != null) {
            if(Client.getInstance().getCli() !=  null) Client.getInstance().getCli().shutdown();
            if(Client.getInstance().getNetworkController() != null) Client.getInstance().getNetworkController().shutdown();
        }

        main(new String[]{serverIp, String.valueOf(serverPort)});
    }

    public Client(String serverIp, int serverPort) throws IOException {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        Socket socket = new Socket(serverIp, serverPort);
        clientState = ClientState.LOGIN;
        gameHandler = null;
        clientController = new ClientController();
        networkController = NetworkController.networkControllerFactory(socket);
    }

    @Override
    public void run() {
        networkController.start();
        cli = new CLI(this);

    }

    public static void main(String[] args) {

        if(args.length == 0) {
            try {
                singleton = new Client("localhost", 23154);
                singleton.run();
            } catch (IOException e) {
                System.out.println(ConsoleColor.RED + "Failed to connect to the server" + ConsoleColor.RESET);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                restart("localhost", 23154);
            }
        } else if (args.length == 2) {
            try {
                singleton = new Client(args[0], Integer.parseInt(args[1]));
                singleton.run();
            } catch (IOException e) {
                System.out.println(ConsoleColor.RED + "Failed to connect to the server on address " + args[0] + " " + args[1] + ConsoleColor.RESET);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                restart(args[0], Integer.parseInt(args[1]));
            }
        } else
            System.out.println(ConsoleColor.RED + "Invalid number of args to start the client" + ConsoleColor.RESET);
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
