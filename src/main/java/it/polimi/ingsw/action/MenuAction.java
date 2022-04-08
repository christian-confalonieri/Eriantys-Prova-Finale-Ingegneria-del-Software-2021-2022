package it.polimi.ingsw.action;

import it.polimi.ingsw.server.Server;

public class MenuAction extends Action {

    private Server serverApp;

    @Override
    public void init(Server serverApp) {
        super.init(serverApp);
        this.serverApp = serverApp;
    }
}
