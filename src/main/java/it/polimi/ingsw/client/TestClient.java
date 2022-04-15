package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
    Socket socket;
    PrintWriter output;
    Scanner input;

    public TestClient(String serverIp, int serverPort) throws IOException {
        socket = new Socket(serverIp, serverPort);
        try {
            output = new PrintWriter(socket.getOutputStream());
            input = new Scanner(socket.getInputStream()).useDelimiter("\r\n"); // TODO Check if correct
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scanner getInputScanner() {
        return input;
    }

    public void send(String message) {
        output.println(message);
        output.flush();
    }
}
