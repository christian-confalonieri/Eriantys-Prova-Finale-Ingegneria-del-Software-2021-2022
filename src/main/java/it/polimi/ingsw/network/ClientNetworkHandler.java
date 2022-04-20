package it.polimi.ingsw.network;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * A ClientNetworkHandler is an object used by the server to manage a network connection with a client.
 * Every ClientNetworkHandler has a socket connected to client and a listener thread that listens for the messages
 * coming from the socket and calls the controller that handle the messages
 *
 */
public class ClientNetworkHandler implements Runnable {
    private Socket clientSocket;
    Thread listenerThread;
    private boolean shutdown;

    private ClientNetworkHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

        // try {
        //    clientSocket.setSoTimeout(10000); // Sets the timeout to 10s for reading operations
        //} catch (SocketException e) {
        //    e.printStackTrace();
        //}
        shutdown = false;
    }

    /**
     * Construct a ClientNetworkHandler starting the listener thread and store new netHandler in the server
     * @param clientSocket The socket to associate with the ClientNetHanlder
     */
    public static void clientNetworkHandlerFactory(Socket clientSocket) {
        ClientNetworkHandler clientNetworkHandler = new ClientNetworkHandler(clientSocket);
        Server.getInstance().addClientConnection(clientNetworkHandler);

        clientNetworkHandler.listenerThread = new Thread(clientNetworkHandler);
        clientNetworkHandler.listenerThread.start();
    }

    /**
     * Close the socket connection associated with the handler, stops the listener thread and remove
     * the object from the server
     */
    public void shutdown() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(this.toString() + " lost connection");
        }
        Server.getInstance().removeClientConnection(this);
        listenerThread.interrupt();
        shutdown = true;
    }

    /**
     * Listens for the messages via network and call the server controller that execute the actions recieved
     */
    @Override
    public void run() {
        System.out.println(this.toString() + " listening...");
        InputStream inputStream = null;

        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            this.shutdown();
            return;
        }

        Scanner s = new Scanner(inputStream).useDelimiter("\n");
        String ipAddress = clientSocket.getInetAddress().toString();

        while (!shutdown) {
            // TODO Define a close connection message and a ack system to close broken connections
            if(s.hasNext()) // Blocking:waits for input (But if client disconnects deadlock)
                Server.getInstance().getGameController().actionExecutor(s.next(), this);
        }
        System.out.println(ConsoleColor.RED + this.toString() + " listening thread closed" + ConsoleColor.RESET);
    }

    public void send(String obj) {
        if (obj != null) {
            try {
                PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
                pw.println(obj);
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String toString() {
        return "@" + clientSocket.getInetAddress() + ":" + clientSocket.getPort();
    }
}
