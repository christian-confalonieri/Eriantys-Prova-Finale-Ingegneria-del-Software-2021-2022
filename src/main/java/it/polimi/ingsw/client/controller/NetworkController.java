package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.action.LogoutAction;
import it.polimi.ingsw.action.PING;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Scanner;

public class NetworkController implements Runnable {
    private static final int PING_EVERY_MS = 10000;
    private static final int WAIT_PONG_FOR_MS = 5000;

    private final Socket socket;
    private Thread listenerThread;
    private boolean shutdown;
    private volatile Thread timerThread;

    private Thread pollingPingThread;

    private long lastPongedTime;

    private NetworkController(Socket socket) {
        this.socket = socket;
        shutdown = false;
    }

    public static NetworkController networkControllerFactory(Socket socket) {
        NetworkController networkController = new NetworkController(socket);

        networkController.listenerThread = new Thread(networkController);
        networkController.pollingPingThread = new Thread(networkController::pollingPing);
        return networkController;
    }

    public void start() {
        try {
            this.listenerThread.start();
            this.pollingPingThread.start();
        } catch (IllegalThreadStateException e) {
            System.out.println("Network threads not started as threads were interrupted");
        }
    }

    public void shutdown() {
        shutdown = true;
        try {
            socket.close();
            pollingPingThread.interrupt();
        } catch (IOException e) {
            System.out.println("Couldn't close the socket safely");
        }
    }

    @Override
    public void run() {
        InputStream inputStream = null;

        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            this.shutdown();
            return;
        }

        Scanner s = new Scanner(inputStream).useDelimiter("\n");

        while(!shutdown) {
            if(s.hasNext()) {
                Client.getInstance().getClientController().actionExecutor(s.next());
            }
        }

        System.out.println(ConsoleColor.RED + "Listening thread closed" + ConsoleColor.RESET);
    }

    public void startTimerPongResponse() {
        long startTimer = System.currentTimeMillis();
        timerThread = new Thread(() -> this.pongTimer(startTimer));
        timerThread.start();
        this.send(ClientActionHandler.toJson(new PING()));
    }

    private void pongTimer(long startTimer) {
        try {

            Thread.sleep(WAIT_PONG_FOR_MS);

            boolean hasBeenPonged;
            synchronized( this ) {
                hasBeenPonged = lastPongedTime > startTimer;
            }

            if(!hasBeenPonged) {
                System.out.println(ConsoleColor.RED + "Server did not ponged back in " + (WAIT_PONG_FOR_MS / 1000) + "s. Logging out and resetting" + ConsoleColor.RESET);
                LoginService.logout(new LogoutAction(Client.getInstance().getPlayerId())); // Logout te client without contacting the server (as its not respoding)
                Client.getInstance().restartNetwork(Client.getInstance().serverIp, Client.getInstance().serverPort);
            }
        } catch (InterruptedException e) {
            // Thread interrupted by a pong receive
        }
    }

    public void stopPongTimerThread() {
        while (timerThread == null) Thread.onSpinWait();
        timerThread.interrupt();
    }

    synchronized public void setPonged() {
        lastPongedTime = System.currentTimeMillis();
    }

    private void pollingPing() {
        while(!shutdown) {
            Client.getInstance().getNetworkController().startTimerPongResponse();
            if(Client.getInstance().isPollAllLobbies()) LobbyService.getAllLobbysRequest();
            try {
                Thread.sleep(PING_EVERY_MS);
            } catch (InterruptedException e) {
                System.out.println(ConsoleColor.YELLOW + "PollingPingThread interrupted" + ConsoleColor.RESET);
            }
        }
        System.out.println(ConsoleColor.RED + "PollingPingThread closed" + ConsoleColor.RESET);
    }

    public void send(String obj) {
        if (obj != null) {
            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(obj);
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
