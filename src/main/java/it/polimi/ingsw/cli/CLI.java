package it.polimi.ingsw.cli;

import it.polimi.ingsw.model.game.GameHandler;

public class CLI {

    private GameHandler gameHandler;

    public CLI (GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void printGameName () {
        System.out.println("ERYANTIS\n-----------------------");
    }

    public void printBag () {
        System.out.println("   ___\n" +
                "  /   \\\n" +
                " |__%__|\n" +
                " / : : \\\n" +
                "(       )\n" +
                " `-----'");
    }

    public String cliPlayers () {
        String res =  "";
        for (int i = 0; i < gameHandler.getOrderedTurnPlayers().size(); i++) {
            if ( i != gameHandler.getOrderedTurnPlayers().size() - 1 ) {
                res += gameHandler.getOrderedTurnPlayers().get(i) + " Vs. ";
            } else {
                res += gameHandler.getOrderedTurnPlayers().get(i);
            }
        }
        return res;
    }

    public void printPlayers (String playersString) {
        System.out.println(cliPlayers());
    }

}
