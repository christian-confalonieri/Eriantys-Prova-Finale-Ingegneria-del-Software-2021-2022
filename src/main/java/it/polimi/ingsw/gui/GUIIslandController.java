package it.polimi.ingsw.gui;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GUIIslandController {
    public AnchorPane anchorPaneIsland;
    public ImageView outlineIsland;
    public ImageView island;
    public ImageView motherNature;

    public void selectIsland() {
        outlineIsland.setVisible(true);
    }
    public void deselectIsland() {
        outlineIsland.setVisible(false);
    }
}
