package it.polimi.ingsw.action;

public class LoginAction extends MenuAction {

    public LoginAction(String playerId) {
        super(ActionType.LOGIN, playerId);
    }

    @Override
    public void execute() {
        System.out.println("Login effettuato...");
        if (playerId.isBlank() || playerId.isEmpty()) {
            // Generate a new id
            // Will return the serialized id to send back
        }
        else {
            // check if present and connected to a game
            // Will return the serialized id and the game where it was logged in
        }
    }
}
