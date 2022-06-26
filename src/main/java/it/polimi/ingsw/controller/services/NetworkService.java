package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.PING;
import it.polimi.ingsw.action.PONG;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;

public class NetworkService {
    public static void recvPing(PING action, ClientNetworkHandler clientNet) {
        System.out.println(ConsoleColor.BLUE + clientNet + " server responded to ping" + ConsoleColor.RESET);
        clientNet.send(ActionHandler.toJson(new PONG()));
    }

    public static void recvPong(PONG action, ClientNetworkHandler clientNet) {
        clientNet.setPonged();
        clientNet.stopPongTimerThread();
        System.out.println(ConsoleColor.GREEN + clientNet + " ponged back" + ConsoleColor.RESET);
    }
}
