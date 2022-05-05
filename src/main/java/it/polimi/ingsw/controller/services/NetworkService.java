package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.PING;
import it.polimi.ingsw.action.PONG;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;

public class NetworkService {
    public static void recvPing(PING action, ClientNetworkHandler clientNet) {
        clientNet.send(ActionHandler.toJson(new PONG()));
    }

    public static void recvPong(PONG action, ClientNetworkHandler clientNet) {
        clientNet.setPonged(true);
        clientNet.stopPongTimerThread();
        System.out.println(ConsoleColor.GREEN + clientNet + " ponged back" + ConsoleColor.RESET);
    }
}
