package it.polimi.ingsw.network;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerNetworkHandler handle a server port by accepting connections from clients, and associating a new ClientNetworkHandler
 * to each new client connected
 */
public class ServerNetworkHandler implements Runnable {
    private static final int PING_EVERY_MS = 10000;

    private final ServerSocket serverSocket;
    private boolean shutdown;
    private Thread pollingPingThread;

    public ServerNetworkHandler(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        shutdown = false;
    }

    public void shutdown() {
        shutdown = true;
    }

    public void run() {
        System.out.println(ConsoleColor.GREEN + "ServerNetworkHandler started on port " + serverSocket.getLocalPort() + ConsoleColor.RESET);

        // Sets up the polling ping thread
        pollingPingThread = new Thread(this::pollingPing);
        pollingPingThread.start();

        while(!shutdown) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println(ConsoleColor.GREEN_BRIGHT + "@" + clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort() + " connected..." + ConsoleColor.RESET);

                ClientNetworkHandler.clientNetworkHandlerFactory(clientSocket); // Launch a thread starting the listening

            } catch (IOException e) {
                e.printStackTrace();
                this.shutdown();
            }
        }

        System.out.println(ConsoleColor.RED + "ServerNetworkHandler stopped: Not accepting new connections" + ConsoleColor.RESET);
    }

    private void pollingPing() {
        while(!shutdown) {
            System.out.println(ConsoleColor.PURPLE + "Pinging all [" + Server.getInstance().getClientConnectionsSize()
                    + "] client connections. Expecting some pongs..." + ConsoleColor.RESET);
            Server.getInstance().forEachClientConnection(ClientNetworkHandler::startTimerPongResponse);

            try {
                Thread.sleep(PING_EVERY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
