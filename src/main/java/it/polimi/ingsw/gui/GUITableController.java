package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Island;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class GUITableController {

    @FXML
    private AnchorPane island1;
    @FXML
    private AnchorPane island2;
    @FXML
    private AnchorPane island3;
    @FXML
    private AnchorPane island4;
    @FXML
    private AnchorPane island5;
    @FXML
    private AnchorPane island6;
    @FXML
    private AnchorPane island7;
    @FXML
    private AnchorPane island8;
    @FXML
    private AnchorPane island9;
    @FXML
    private AnchorPane island10;
    @FXML
    private AnchorPane island11;
    @FXML
    private AnchorPane island12;
    
    @FXML
    private GUIIslandController island1Controller;
    @FXML
    private GUIIslandController island2Controller;
    @FXML
    private GUIIslandController island3Controller;
    @FXML
    private GUIIslandController island4Controller;
    @FXML
    private GUIIslandController island5Controller;
    @FXML
    private GUIIslandController island6Controller;
    @FXML
    private GUIIslandController island7Controller;
    @FXML
    private GUIIslandController island8Controller;
    @FXML
    private GUIIslandController island9Controller;
    @FXML
    private GUIIslandController island10Controller;
    @FXML
    private GUIIslandController island11Controller;
    @FXML
    private GUIIslandController island12Controller;

    
    @FXML
    private AnchorPane cloud1;
    @FXML
    private AnchorPane cloud2;
    @FXML
    private AnchorPane cloud3;
    @FXML
    private AnchorPane cloud4;

    @FXML
    private GUICloudController cloud1Controller;
    @FXML
    private GUICloudController cloud2Controller;
    @FXML
    private GUICloudController cloud3Controller;
    @FXML
    private GUICloudController cloud4Controller;


    
    @FXML
    private AnchorPane playerT1;
    @FXML
    private AnchorPane playerT2;
    @FXML
    private AnchorPane playerT3;
    @FXML
    private AnchorPane playerT4;

    @FXML
    private GUIPlayerTableController playerT1Controller;
    @FXML
    private GUIPlayerTableController playerT2Controller;
    @FXML
    private GUIPlayerTableController playerT3Controller;
    @FXML
    private GUIPlayerTableController playerT4Controller;

    
    
    @FXML
    private AnchorPane power1;
    @FXML
    private AnchorPane power2;
    @FXML
    private AnchorPane power3;

    @FXML
    private GUIPowerController power1Controller;
    @FXML
    private GUIPowerController power2Controller;
    @FXML
    private GUIPowerController power3Controller;


    protected void initializeTable() {
        List<Island> islands = Client.getInstance().getGameHandler().getGame().getIslands();
        island1Controller.setIslandModel(islands.get(0));
        island2Controller.setIslandModel(islands.get(1));
        island3Controller.setIslandModel(islands.get(2));
        island4Controller.setIslandModel(islands.get(3));
        island5Controller.setIslandModel(islands.get(4));
        island6Controller.setIslandModel(islands.get(5));
        island7Controller.setIslandModel(islands.get(6));
        island8Controller.setIslandModel(islands.get(7));
        island9Controller.setIslandModel(islands.get(8));
        island10Controller.setIslandModel(islands.get(9));
        island11Controller.setIslandModel(islands.get(10));
        island12Controller.setIslandModel(islands.get(11));
    }

    public void render() {
        renderIsland(island1, island1Controller, 26, 154);
        renderIsland(island2, island2Controller, 120, 69);
        renderIsland(island3, island3Controller, 242, -7);
        renderIsland(island4, island4Controller, 368, -7);
        renderIsland(island5, island5Controller, 485, 69);
        renderIsland(island6, island6Controller, 590, 167);
        renderIsland(island7, island7Controller, 589, 300);
        renderIsland(island8, island8Controller, 485, 388);
        renderIsland(island9, island9Controller, 361, 458);
        renderIsland(island10, island10Controller, 233, 458);
        renderIsland(island11, island11Controller, 120, 385);
        renderIsland(island12, island12Controller, 25, 300);
    }

    private void renderIsland(AnchorPane islandPane, GUIIslandController islandController, double x, double y) {
        if(Client.getInstance().getGameHandler().getGame().getIslands().contains(islandController.getIslandModel())) {
            islandPane.setLayoutX(x);
            islandPane.setLayoutY(y);
            islandController.render();
        }
        else {
            islandController.remove();
        }

    }

}
