package it.polimi.ingsw.action;

import it.polimi.ingsw.server.GameLobby;
import java.util.List;

public class GetAllLobbysAction extends MenuAction {

    List<GameLobby> gameLobbyList;

    public GetAllLobbysAction(String playerId) {
        super(ActionType.GETALLLOBBYS, playerId);
    }

    public List<GameLobby> getGameLobbyList() {
        return gameLobbyList;
    }

    public void setGameLobbyList(List<GameLobby> gameLobbyList) {
        this.gameLobbyList = gameLobbyList;
    }
}
