package it.polimi.ingsw.client;

import it.polimi.ingsw.action.PlayCardAction;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    /**
     * This test simulates the request of a client connected to a server
     */
    @Test
    void ClientLoginLogout() throws InterruptedException {
        String[] args = {};
        new Thread(() -> Client.main(args)).start();
        Thread.sleep(1000);

        assertEquals(Client.getInstance().getClientState(), ClientState.LOGIN);
        assertNull(Client.getInstance().getPlayerId());

        LoginService.loginRequest("leotheking");
        Thread.sleep(1000);

        assertEquals(Client.getInstance().getClientState(), ClientState.MAINMENU);
        assertEquals(Client.getInstance().getPlayerId(), "leotheking");

        LoginService.logoutRequest();
        Thread.sleep(1000);

        assertEquals(Client.getInstance().getClientState(), ClientState.LOGIN);
        assertNull(Client.getInstance().getPlayerId());
    }

    @Test
    void ClientNewGame() throws InterruptedException {
        String[] args = {};
        new Thread(() -> Client.main(args)).start();
        Thread.sleep(1000);

        assertEquals(Client.getInstance().getClientState(), ClientState.LOGIN);
        assertNull(Client.getInstance().getPlayerId());

        LoginService.loginRequest("leotheking");
        Thread.sleep(1000);

        LobbyService.newGameRequest(2, null, Wizard.BLUE);
        Thread.sleep(1000);

        assertNotNull(Client.getInstance().getGameLobby());
        assertTrue(Client.getInstance().getGameLobby().isPlayerWaiting("leotheking"));

        LobbyService.getAllLobbysRequest();
        Thread.sleep(1000);
        assertEquals(Client.getInstance().getAllServerLobbys().get(0).getGameLobbyId(), Client.getInstance().getGameLobby().getGameLobbyId());

        Thread.sleep(100000);
        LoginService.logoutRequest();
        Thread.sleep(1000);
    }

    @Test
    void ClientJoinGame() throws InterruptedException {
        String[] args = {};
        new Thread(() -> Client.main(args)).start();
        Thread.sleep(1000);

        LoginService.loginRequest("Luigi");
        Thread.sleep(1000);

        do {
            LobbyService.getAllLobbysRequest();
            Thread.sleep(100);
        } while(Client.getInstance().getAllServerLobbys() == null || Client.getInstance().getAllServerLobbys().isEmpty());

        LobbyService.joinGameRequest(Client.getInstance().getAllServerLobbys().get(0).getGameLobbyId(), Wizard.BLUE);
        Thread.sleep(1000);
        assertEquals(Client.getInstance().getClientState(), ClientState.MAINMENU);

        LobbyService.joinGameRequest(Client.getInstance().getAllServerLobbys().get(0).getGameLobbyId(), Wizard.GREEN);
        Thread.sleep(1000);


        assertEquals(Client.getInstance().getClientState(), ClientState.INGAME);
        assertNotNull(Client.getInstance().getGameHandler());
        assertTrue(Client.getInstance().getGameHandler().getGame().getPlayers().stream().map(Player::getName).toList().contains("Luigi"));
    }

    @Test
    void Client1GameService() throws InterruptedException {
        String[] args = {};
        new Thread(() -> Client.main(args)).start();
        Thread.sleep(1000);

        LoginService.loginRequest("Mario");
        Thread.sleep(1000);

        LobbyService.newGameRequest(2, null, Wizard.BLUE);
        Thread.sleep(1000);

        LobbyService.getAllLobbysRequest();

        /* PlayCardRequest Testing ----------------------------------------------------------------------------------------*/
        for(int i=15; i>0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        GameService.playCardRequest(Client.getInstance().getGameHandler().getCurrentPlayer().getHandCards().get(3));
        Thread.sleep(1000);
        assertEquals(4,Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getLastPlayedCard().getNumber());
        assertFalse(Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getHandCards().contains(Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getLastPlayedCard()));
    }

    @Test
    void Client2GameService() throws InterruptedException {
        String[] args = {};
        new Thread(() -> Client.main(args)).start();
        Thread.sleep(1000);

        LoginService.loginRequest("Luigi");
        Thread.sleep(1000);

        do {
            LobbyService.getAllLobbysRequest();
            Thread.sleep(100);
        } while(Client.getInstance().getAllServerLobbys() == null || Client.getInstance().getAllServerLobbys().isEmpty());

        LobbyService.joinGameRequest(Client.getInstance().getAllServerLobbys().get(0).getGameLobbyId(), Wizard.GREEN);
        Thread.sleep(1000);

        /* PlayCardRequest Testing ----------------------------------------------------------------------------------------*/
        for(int i=15; i>0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        GameService.playCardRequest(Client.getInstance().getGameHandler().getCurrentPlayer().getHandCards().get(2));
        Thread.sleep(1000);
        assertEquals(3,Client.getInstance().getGameHandler().getGame().getPlayers().get(1).getLastPlayedCard().getNumber());
        assertFalse(Client.getInstance().getGameHandler().getGame().getPlayers().get(1).getHandCards().contains(Client.getInstance().getGameHandler().getGame().getPlayers().get(1).getLastPlayedCard()));
    }
}