package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.PING;
import it.polimi.ingsw.action.PONG;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;

public class NetworkService {
    /**
     * Responds with a pong if a ping is received
     * @param action The PING action
     * @param clientNet The client network handler that sent te ping
     */
    public static void recvPing(PING action, ClientNetworkHandler clientNet) {
        System.out.println(ConsoleColor.BLUE + clientNet + " server responded to ping" + ConsoleColor.RESET);
        clientNet.send(ActionHandler.toJson(new PONG()));
    }

    /**
     * Stops the counting timer associated with the client when a pong is received, responding to a pong sent by the server
     * @param action The PONG action
     * @param clientNet The client network handler that sent te pong
     */
    public static void recvPong(PONG action, ClientNetworkHandler clientNet) {
        clientNet.setPonged();
        clientNet.stopPongTimerThread();
        System.out.println(ConsoleColor.GREEN + clientNet + " ponged back" + ConsoleColor.RESET);
    }
}
