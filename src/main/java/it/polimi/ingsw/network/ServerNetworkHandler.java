package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetworkHandler {

    ServerSocket serverSocket;

    public ServerNetworkHandler(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        System.out.println("ServerNetworkHandler started...");
        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket.getInetAddress().toString() + " connected...");

            ClientNetworkHandler clientNetHandler = new ClientNetworkHandler(clientSocket); // Launch a thread starting the listening
            Server.getInstance().addClientConnection(clientNetHandler);
        }
    }
}
