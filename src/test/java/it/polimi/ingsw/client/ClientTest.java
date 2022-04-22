package it.polimi.ingsw.client;

import com.sun.source.tree.AssertTree;
import it.polimi.ingsw.action.PlayCardAction;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.power.Friar;
import it.polimi.ingsw.model.power.Herald;
import it.polimi.ingsw.model.power.Mailman;
import it.polimi.ingsw.model.power.PowerCard;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @author Christian Confalonieri
     */
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
        Thread.sleep(1000);

        /* PlayCardRequest Testing ----------------------------------------------------------------------------------------*/
        for(int i=15; i>0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }

        GameService.playCardRequest(Client.getInstance().getGameHandler().getCurrentPlayer().getHandCards().get(2));
        Thread.sleep(1000);
        assertEquals(3,Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getLastPlayedCard().getNumber());
        assertFalse(Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getHandCards().contains(Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getLastPlayedCard()));

        /* Power Testing --------------------------------------------------------------------------------------------------*/
        for(int i=15; i>0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }

//        List<PowerCard> powerCards = new ArrayList<>();
//        powerCards.add(new Mailman(Client.getInstance().getGameHandler()));
//        powerCards.add(new Friar(Client.getInstance().getGameHandler()));
//        powerCards.add(new Herald(Client.getInstance().getGameHandler()));
//        Server.getInstance().getGameHandler().getGame().setPowerCards(powerCards); //TODO IN THE SERVER
//
//
//        assertEquals(1,Client.getInstance().getGameHandler().getGame().getPowerCards().get(0).getCost()); // mailman cost = 1
//        assertEquals(1,Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getCoins());
//
//        GameService.powerRequest(PowerType.MAILMAN,Client.getInstance().getGameHandler().getGame().getEffectHandler());
//        Thread.sleep(1000);
//
//        assertEquals(2,Client.getInstance().getGameHandler().getGame().getPowerCards().get(0).getCost()); // mailman cost = 2
//        assertEquals(0,Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getCoins());

        /* MoveStudents Testing -------------------------------------------------------------------------------------------*/

        List<Student> toDiningRoom = new ArrayList<>();
        toDiningRoom.add(Client.getInstance().getGameHandler().getCurrentPlayer().getSchool().getEntrance().get(0));
        Map<Student,String>  toIslands = new HashMap<>();
        List<Student> toIslandStudents = new ArrayList<>();
        toIslandStudents.add(Client.getInstance().getGameHandler().getCurrentPlayer().getSchool().getEntrance().get(1));
        toIslandStudents.add(Client.getInstance().getGameHandler().getCurrentPlayer().getSchool().getEntrance().get(2));
        toIslands.put(Client.getInstance().getGameHandler().getCurrentPlayer().getSchool().getEntrance().get(1),Client.getInstance().getGameHandler().getGame().getIslands().get(0).getUuid());
        toIslands.put(Client.getInstance().getGameHandler().getCurrentPlayer().getSchool().getEntrance().get(2),Client.getInstance().getGameHandler().getGame().getIslands().get(1).getUuid());

        assertEquals(7,Client.getInstance().getGameHandler().getCurrentPlayer().getSchool().getEntrance().size());
        assertFalse(Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getSchool().getDiningRoom().get(toDiningRoom.get(0).getColor()).contains(toDiningRoom.get(0)));
        assertFalse(Client.getInstance().getGameHandler().getGame().getIslands().get(0).getStudents().contains(toIslandStudents.get(0)));
        assertFalse(Client.getInstance().getGameHandler().getGame().getIslands().get(1).getStudents().contains(toIslandStudents.get(1)));

        GameService.moveStudentsRequest(toDiningRoom,toIslands);
        Thread.sleep(1000);

        assertEquals(4,Client.getInstance().getGameHandler().getCurrentPlayer().getSchool().getEntrance().size());
        assertTrue(Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getSchool().getDiningRoom().get(toDiningRoom.get(0).getColor()).contains(toDiningRoom.get(0)));
        assertTrue(Client.getInstance().getGameHandler().getGame().getIslands().get(0).getStudents().contains(toIslandStudents.get(0)));
        assertTrue(Client.getInstance().getGameHandler().getGame().getIslands().get(1).getStudents().contains(toIslandStudents.get(1)));

        /* MoveMotherNature Testing -------------------------------------------------------------------------------------------*/

        String motherNatureIsland = Client.getInstance().getGameHandler().getGame().getMotherNature().isOn().getUuid();

        GameService.moveMotherNatureRequest(2);
        Thread.sleep(1000);

        assertEquals(Client.getInstance().getGameHandler().getGame().getIslandFromId(motherNatureIsland).getNextIsland().getNextIsland(),Client.getInstance().getGameHandler().getGame().getMotherNature().isOn());

        /* MoveCloud Testing --------------------------------------------------------------------------------------------------*/

        List<Student> students = new ArrayList<>(Client.getInstance().getGameHandler().getGame().getClouds().get(0).getStudents());

        assertEquals(3,Client.getInstance().getGameHandler().getGame().getClouds().get(0).getStudents().size());
        assertEquals(4,Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getSchool().getEntrance().size());
        assertTrue(Client.getInstance().getGameHandler().getGame().getClouds().get(0).getStudents().containsAll(students));
        assertFalse(Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getSchool().getEntrance().containsAll(students));

        GameService.moveCloudRequest(Client.getInstance().getGameHandler().getGame().getClouds().get(0));
        Thread.sleep(1000);

        assertEquals(0,Client.getInstance().getGameHandler().getGame().getClouds().get(0).getStudents().size());
        assertEquals(7,Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getSchool().getEntrance().size());
        assertFalse(Client.getInstance().getGameHandler().getGame().getClouds().get(0).getStudents().containsAll(students));
        assertTrue(Client.getInstance().getGameHandler().getGame().getPlayers().get(0).getSchool().getEntrance().containsAll(students));
    }

    /**
     * @author Christian Confalonieri
     */
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
        GameService.playCardRequest(Client.getInstance().getGameHandler().getCurrentPlayer().getHandCards().get(3));
        Thread.sleep(1000);
        assertEquals(4,Client.getInstance().getGameHandler().getGame().getPlayers().get(1).getLastPlayedCard().getNumber());
        assertFalse(Client.getInstance().getGameHandler().getGame().getPlayers().get(1).getHandCards().contains(Client.getInstance().getGameHandler().getGame().getPlayers().get(1).getLastPlayedCard()));
    }
}