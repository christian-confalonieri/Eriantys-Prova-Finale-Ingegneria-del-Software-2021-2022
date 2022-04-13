package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    @Test
    void TwoPlayerGameStart() {
        String[] args = {};
        Server.main(args);

    }
}