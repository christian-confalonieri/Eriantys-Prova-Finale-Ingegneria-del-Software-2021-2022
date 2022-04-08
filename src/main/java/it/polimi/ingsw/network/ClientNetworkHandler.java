package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClientNetworkHandler implements Runnable{
    Socket clientSocket;

    public ClientNetworkHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        new Thread(this).start();
    }



    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner s = new Scanner(inputStream).useDelimiter("\n");
        System.out.println("Thread started listening");
        while (true) {
            if(s.hasNext()) {
                String result = s.next();

                System.out.println(result);
                Server.getInstance().getGameController().actionExecutor(result);
            }
        }
    }
}
