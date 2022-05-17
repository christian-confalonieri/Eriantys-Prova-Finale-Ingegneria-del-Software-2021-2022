module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens it.polimi.ingsw.gui to javafx.fxml;
    exports it.polimi.ingsw.gui;
}