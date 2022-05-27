package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Cloud;
import it.polimi.ingsw.model.entity.Island;
import javafx.fxml.FXML;
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

    /**
     * @author Leonardo Airoldi, Christian Confalonieri
     */
    protected void initializeTable() {
        List<Island> islands = Client.getInstance().getGameHandler().getGame().getIslands();
        Iterator<Island> islandIterator = islands.iterator();

        List<Cloud> clouds = Client.getInstance().getGameHandler().getGame().getClouds();
        Iterator<Cloud> cloudIterator = clouds.iterator();

        allIslandExecute(((anchorPane, guiIslandController) -> guiIslandController.setIslandModel(islandIterator.next())));
        allCloudExecute(((anchorPane, guiCloudController) -> guiCloudController.setCloudModel(cloudIterator.next())));

        int[] islandsN = new int[islands.size()];
        for(int i=1;i<=islands.size();i++) {
            islandsN[i-1] = i;
        }
        Iterator<Integer> islandsNIt = Arrays.stream(islandsN).iterator();
        allIslandExecute((((anchorPane, guiIslandController) -> guiIslandController.setIslands(islandsNIt.next()))));

        int[] cloudsN = new int[clouds.size()];
        for(int i=1;i<=clouds.size();i++) {
            cloudsN[i-1] = i;
        }
        Iterator<Integer> cloudsNIt = Arrays.stream(cloudsN).iterator();
        allCloudExecute((((anchorPane, guiCloudController) -> guiCloudController.setClouds(cloudsNIt.next()))));
    }

    /**
     * @author Leonardo Airoldi, Christian Confalonieri
     */
    public void render() {
        int[] islandsX = new int[Client.getInstance().getGameHandler().getGame().getIslands().size()];
        int[] islandsY = new int[Client.getInstance().getGameHandler().getGame().getIslands().size()];
        for(int i=1;i<=Client.getInstance().getGameHandler().getGame().getIslands().size();i++) {
            islandsX[i-1] = switch (i) {
                case 1,12 -> 77;
                case 2,11 -> 162;
                case 3,10 -> 267;
                case 4,9 -> 386;
                case 5,8 -> 495;
                case 6,7 -> 573;
                default -> 0;
            };
            islandsY[i-1] = switch (i) {
                case 1,6 -> 202;
                case 2,5 -> 98;
                case 3,4 -> 28;
                case 7,12 -> 316;
                case 8,11 -> 422;
                case 9,10 -> 481;
                default -> 0;
            };
        }
        int[] cloudsX = new int[Client.getInstance().getGameHandler().getGame().getClouds().size()];
        int[] cloudsY = new int[Client.getInstance().getGameHandler().getGame().getClouds().size()];
        for(int i=1;i<=Client.getInstance().getGameHandler().getGame().getClouds().size();i++) {
            if(Client.getInstance().getGameHandler().getGame().getClouds().size() == 2) {
                cloudsX[i-1] = switch (i) {
                    case 1 -> 213;
                    case 2-> 444;
                    default -> 0;
                };
                cloudsY[i-1] = switch (i) {
                    case 1,2 -> 247;
                    default -> 0;
                };
            }
            else {
                cloudsX[i-1] = switch (i) {
                    case 1 -> 213;
                    case 2,4 -> 328;
                    case 3 -> 444;
                    default -> 0;
                };
                cloudsY[i-1] = switch (i) {
                    case 1,3 -> 247;
                    case 2 -> 158;
                    case 4 -> 345;
                    default -> 0;
                };
            }
        }

        Iterator<Integer> islandsXIt = Arrays.stream(islandsX).iterator();
        Iterator<Integer> islandsYIt = Arrays.stream(islandsY).iterator();
        Iterator<Integer> cloudsXIt = Arrays.stream(cloudsX).iterator();
        Iterator<Integer> cloudsYIt = Arrays.stream(cloudsY).iterator();

        allIslandExecute((islandPane, islandController) -> renderIsland(islandPane, islandController, islandsXIt.next(), islandsYIt.next()));
        allCloudExecute((cloudPane, cloudController) -> renderCloud(cloudPane, cloudController, cloudsXIt.next(), cloudsYIt.next()));
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

    /**
     * @author Christian Confalonieri
     */
    private void renderCloud(AnchorPane cloudPane, GUICloudController cloudController, double x, double y) {
        if(Client.getInstance().getGameHandler().getGame().getClouds().contains(cloudController.getCloudModel())) {
            cloudPane.setLayoutX(x);
            cloudPane.setLayoutY(y);
            cloudController.render();
        }
        else {
            cloudController.remove();
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

    /**
     * @author Christian Confalonieri
     */
    public void allCloudExecute(BiConsumer<AnchorPane, GUICloudController> function) {
        function.accept(cloud1, cloud1Controller);
        function.accept(cloud2, cloud2Controller);
        if(Client.getInstance().getGameHandler().getGame().getClouds().size() == 2) {
            cloud3Controller.remove();
            cloud4Controller.remove();
            return;
        }
        function.accept(cloud3, cloud3Controller);
        if(Client.getInstance().getGameHandler().getGame().getClouds().size() == 3) {
            cloud4Controller.remove();
            return;
        }
        function.accept(cloud4, cloud4Controller);
    }

}
