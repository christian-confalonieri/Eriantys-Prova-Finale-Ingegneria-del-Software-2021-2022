module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens it.polimi.ingsw.gui to javafx.fxml;
    exports it.polimi.ingsw.gui;
    opens it.polimi.ingsw.action to com.google.gson;
    exports it.polimi.ingsw.action;
    opens it.polimi.ingsw.server to com.google.gson;
    exports it.polimi.ingsw.server;
    opens it.polimi.ingsw.model.entity to com.google.gson;
    exports it.polimi.ingsw.model.entity;
    opens it.polimi.ingsw.model.enumeration to com.google.gson;
    exports it.polimi.ingsw.model.enumeration;
    opens it.polimi.ingsw.model.game.rules to com.google.gson;
    exports it.polimi.ingsw.model.game.rules;
    opens it.polimi.ingsw.model.game to com.google.gson;
    exports it.polimi.ingsw.model.game;
    opens it.polimi.ingsw.model.power to com.google.gson;
    exports it.polimi.ingsw.model.power;
}