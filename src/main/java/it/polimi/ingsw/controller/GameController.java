package it.polimi.ingsw.controller;


import it.polimi.ingsw.action.Action;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.network.ClientNetworkHandler;

public class GameController {
    /**
     * Converts the json received from the network and execute the actions specified in the message
     * @param json The string received from the network
     * @param netHandler the ClientNetworkHandler from where the message arrived
     */
    public void actionExecutor(String json, ClientNetworkHandler netHandler) {
        try {
            Action action = ActionHandler.fromJson(json);
            ActionHandler.actionServiceInvoker(action, netHandler);
        } catch (InvalidAction e) {
            System.out.println(ConsoleColor.YELLOW + netHandler.toString() + " Invalid Action: " + e.getMessage() + ConsoleColor.RESET);
        }
    }

}
