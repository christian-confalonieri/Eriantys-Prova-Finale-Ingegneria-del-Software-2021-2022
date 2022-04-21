package it.polimi.ingsw.client;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.controller.NetworkController;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.game.GameHandler;

import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    public static Client getInstance() {
        return singleton;
    }
    private static Client singleton;

    private ClientState clientState;
    private GameHandler gameHandler;

    private final NetworkController networkController;
    private final ClientController clientController;
    private String playerId;


    public Client(String serverIp, int serverPort) throws IOException {
        Socket socket = new Socket(serverIp, serverPort);
        clientState = ClientState.LOGIN;
        gameHandler = null;
        clientController = new ClientController();
        networkController = NetworkController.networkControllerFactory(socket);
    }

    @Override
    public void run() {
        networkController.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoginService.loginRequest("abcde");
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            try {
                singleton = new Client("localhost", 23154);
                singleton.run();
            } catch (IOException e) {
                System.out.println(ConsoleColor.RED + "Failed to connect to the server" + ConsoleColor.RESET);
            }
        } else if (args.length == 2) {
            try {
                singleton = new Client(args[0], Integer.parseInt(args[1]));
                singleton.run();
            } catch (IOException e) {
                System.out.println(ConsoleColor.RED + "Failed to connect to the server on address " + args[0] + " " + args[1] + ConsoleColor.RESET);
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


}
