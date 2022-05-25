package it.polimi.ingsw.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class GUITableController {

    @FXML
    public AnchorPane anchorPaneIsland1;
    @FXML
    public GUIIslandController anchorPaneIsland1Controller;


    @FXML
    private void logout() {
        anchorPaneIsland1Controller.selectIsland();
        //LoginService.logoutRequest();
    }
}
