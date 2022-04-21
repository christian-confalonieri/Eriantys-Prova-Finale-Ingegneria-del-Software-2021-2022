package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameHandler;
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

        LoginService.loginRequest("confa");
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
        assertTrue(Client.getInstance().getGameHandler().getGame().getPlayers().stream().map(Player::getName).toList().contains("confa"));
        
    }

}