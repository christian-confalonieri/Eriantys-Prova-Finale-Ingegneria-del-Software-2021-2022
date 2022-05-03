package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NetworkController implements Runnable {
    private final Socket socket;
    private Thread listenerThread;
    private boolean shutdown;

    private NetworkController(Socket socket) {
        this.socket = socket;
        shutdown = false;
    }

    public static NetworkController networkControllerFactory(Socket socket) {
        NetworkController networkController = new NetworkController(socket);
        networkController.listenerThread = new Thread(networkController);
        return networkController;
    }

    public void start() {
        this.listenerThread.start();
    }

    public void shutdown() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        shutdown = true;
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
            // TODO Define a close connection message and a ack system to close broken connections
            if(s.hasNext()) { // Blocking:waits for input (But if server disconnects deadlock)
                Client.getInstance().getClientController().actionExecutor(s.next());
                Client.getInstance().getCli().render();
            }
        }
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
