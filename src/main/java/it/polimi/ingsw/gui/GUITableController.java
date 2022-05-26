package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Island;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

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
        Iterator<Island> islandIterator = islands.iterator();

        allIslandExecute(((anchorPane, guiIslandController) -> guiIslandController.setIslandModel(islandIterator.next())));

        int[] n = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Iterator<Integer> nIt = Arrays.stream(n).iterator();
        allIslandExecute((((anchorPane, guiIslandController) -> guiIslandController.setIslandNumber(nIt.next()))));
    }

    public void render() {
        int[] x = new int[]{26, 120, 242, 368, 485, 590, 589, 485, 361, 233, 120, 25};
        int[] y = new int[]{124, 39, -37, -37, 39, 137, 270, 358, 428, 428, 355, 270};

        Iterator<Integer> xIt = Arrays.stream(x).iterator();
        Iterator<Integer> yIt = Arrays.stream(y).iterator();

        allIslandExecute((islandPane, islandController) -> renderIsland(islandPane, islandController, xIt.next(), yIt.next()));
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

    public void allIslandExecute(BiConsumer<AnchorPane, GUIIslandController> function) {
        function.accept(island1, island1Controller);
        function.accept(island2, island2Controller);
        function.accept(island3, island3Controller);
        function.accept(island4, island4Controller);
        function.accept(island5, island5Controller);
        function.accept(island6, island6Controller);
        function.accept(island7, island7Controller);
        function.accept(island8, island8Controller);
        function.accept(island9, island9Controller);
        function.accept(island10, island10Controller);
        function.accept(island11, island11Controller);
        function.accept(island12, island12Controller);
    }

}
