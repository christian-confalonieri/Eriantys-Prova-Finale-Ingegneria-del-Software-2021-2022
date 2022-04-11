package it.polimi.ingsw.network;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetworkHandler implements Runnable {

    private final ServerSocket serverSocket;
    private boolean shutdown;

    public ServerNetworkHandler(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        shutdown = false;
    }

    public void shutdown() {
        shutdown = true;
    }

    public void run() {
        System.out.println(ConsoleColor.GREEN + "ServerNetworkHandler started on port " + serverSocket.getLocalPort() + ConsoleColor.RESET);

        while(!shutdown) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println(ConsoleColor.GREEN + "@" + clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort() + " connected..." + ConsoleColor.RESET);

                new ClientNetworkHandler(clientSocket); // Launch a thread starting the listening

            } catch (IOException e) {
                e.printStackTrace();
                this.shutdown();
            }
        }

        System.out.println(ConsoleColor.RED + "ServerNetworkHandler stopped: Not accepting new connections" + ConsoleColor.RESET);
    }

}
