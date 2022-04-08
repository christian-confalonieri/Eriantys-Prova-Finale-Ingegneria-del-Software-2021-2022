package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClientNetworkHandler implements Runnable{
    private Socket clientSocket;
    private boolean shutdown;

    public ClientNetworkHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

        // try {
        //    clientSocket.setSoTimeout(10000); // Sets the timeout to 10s for reading operations
        //} catch (SocketException e) {
        //    e.printStackTrace();
        //}


        shutdown = false;
        new Thread(this).start();
        Server.getInstance().addClientConnection(this);
    }

    public void shutdown() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("@" + clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort() + " lost connection");
        }
        Server.getInstance().removeClientConnection(this);
        shutdown = true;
    }

    @Override
    public void run() {
        System.out.println("@" + clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort() + " listening...");
        InputStream inputStream = null;

        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            this.shutdown();
            return;
        }

        Scanner s = new Scanner(inputStream).useDelimiter("\n");

        while (!shutdown) {
            // TODO Define a close connection message and a ack system to close broken connections
            if(s.hasNext()) // Blocking:waits for input (But if client disconnects deadlock)
                Server.getInstance().getGameController().actionExecutor(s.next());
        }
        System.out.println("@" + clientSocket.getInetAddress().toString() + ":" + clientSocket.getPort() + " listening thread closed");
    }
}
