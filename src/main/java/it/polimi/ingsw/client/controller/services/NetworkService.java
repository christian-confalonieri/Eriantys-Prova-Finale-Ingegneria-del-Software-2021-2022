package it.polimi.ingsw.client.controller.services;

import it.polimi.ingsw.action.PING;
import it.polimi.ingsw.action.PONG;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.ClientActionHandler;

public class NetworkService {
    public static void recvPing(PING ping) {
        Client.getInstance().getNetworkController().send(ClientActionHandler.toJson(new PONG()));
    }

    public static void recvPong(PONG pong) {
        Client.getInstance().getNetworkController().setPonged(true);
        Client.getInstance().getNetworkController().stopPongTimerThread();
    }
}
