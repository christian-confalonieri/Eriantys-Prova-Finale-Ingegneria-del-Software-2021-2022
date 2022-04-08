package it.polimi.ingsw.action;

public class LoginAction extends MenuAction {

    public LoginAction(String actionType, String playerId) {
        super(actionType, playerId);
    }

    @Override
    public void execute() {
        System.out.println("Login Effettuato");
    }
}
