package it.polimi.ingsw.client.controller;


import it.polimi.ingsw.action.Action;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.exceptions.InvalidAction;

public class ClientController {
    public void actionExecutor(String json) {
        try {
            Action action = ClientActionHandler.fromJson(json);
            ClientActionHandler.actionServiceInvoker(action);
        } catch (InvalidAction e) {
            System.out.println(ConsoleColor.YELLOW + " Invalid Action received from the server: " + e.getMessage() + ConsoleColor.RESET);
        }
    }
}
