package it.polimi.ingsw.network;

import it.polimi.ingsw.action.LogoutAction;
import it.polimi.ingsw.action.PING;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
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

    Thread timerThread;
    private boolean ponged;

    private ClientNetworkHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        shutdown = false;
    }

    /**
     * Construct a ClientNetworkHandler starting the listener thread and store new netHandler in the server
     * @param clientSocket The socket to associate with the ClientNetHandler
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
        shutdown = true;
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(this.toString() + " couldn't close the socket safely");
        }
        Server.getInstance().removeClientConnection(this);
        //listenerThread.interrupt(); Non serve, esce per lo shutdown

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
                System.out.println(this + e.getMessage());
            }
        }
    }

    /**
     * Send a PING message to the handled socket.
     * Then starts a thread that keeps a timer that waits n seconds for a pong response.
     * If the response has not arrived at the end of the timer, the client is set as offline and disconnected
     */
    public void startTimerPongResponse() {
        ponged = false;
        this.send(ActionHandler.toJson(new PING()));
        timerThread = new Thread(this::pongTimer);
        timerThread.start();
    }

    private void pongTimer() {
        if(ponged) return; // PONG received before starting of the timer, check if pong already arrived
        // In case the timer did not start before but is set as false this old timer will perform the same action
        // As the new timer (Wait for 10s and check so no prob)
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // Thread interrupted by a pong receive
            ponged = true;
            return;
        }
        if(!ponged) {
            System.out.println(ConsoleColor.RED + this + " did not ponged back in 10s" + ConsoleColor.RESET);
            if (Server.getInstance().isAssigned(this)) {
                Server.getInstance().getGameController().actionExecutor(ActionHandler.toJson(
                        new LogoutAction(Server.getInstance().getAssignedPlayerId(this))), this);
            }
            this.shutdown();
            return;
        }
        else {
            return;
        }
    }

    public void stopPongTimerThread() {
        try {
            timerThread.interrupt();
        } catch (NullPointerException exception) {
            // TODO Pong recieved before starting the new timer thread.
            // If ignored the system should continue without problems (The thread will not interrupt but will see)
        }
    }

    public void setPonged(boolean ponged) {
        this.ponged = ponged;
    }

    @Override
    public String toString() {
        return "@" + clientSocket.getInetAddress() + ":" + clientSocket.getPort();
    }
}
