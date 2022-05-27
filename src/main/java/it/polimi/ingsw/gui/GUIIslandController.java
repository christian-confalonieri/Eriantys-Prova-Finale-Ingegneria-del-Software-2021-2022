package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUIIslandController {
    @FXML
    public Label labelTower;
    @FXML
    private ImageView tower;
    @FXML
    private Label labelIslandCountAndSelected;
    @FXML
    private ImageView noEntry;
    @FXML
    private ImageView islandBackground;
    @FXML
    private ImageView motherNature;
    @FXML
    private ImageView student1;
    @FXML
    private ImageView student2;
    @FXML
    private ImageView outline;
    @FXML
    private Label labelStudent1;
    @FXML
    private Label labelStudent2;
    @FXML
    private ImageView student5;
    @FXML
    private ImageView student4;
    @FXML
    private Label labelStudent5;
    @FXML
    private Label labelStudent4;
    @FXML
    private ImageView student3;
    @FXML
    private Label labelStudent3;
    @FXML
    private AnchorPane islandPane;


    private Island islandModel;

    protected void setIslandModel(Island islandModel) {
        this.islandModel = islandModel;
    }

    protected Island getIslandModel() {
        return islandModel;
    }

    protected void render() {

    }

    public void remove() {
        islandPane.setDisable(true);
        islandPane.setVisible(false);
    }

    @FXML
    private void selectIsland() {
        if(outline.getOpacity()==0.5) {
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.addSelectedIsland(islandModel));
            labelIslandCountAndSelected.setStyle("-fx-text-fill: RED");
            outline.setOpacity(1);
        }
        else {
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.removeSelectedIsland(islandModel));
            labelIslandCountAndSelected.setStyle("-fx-text-fill: BLACK");
            outline.setOpacity(0.5);
        }
    }

    @FXML
    private void highlightIsland() {
        if(outline.getOpacity() == 0) {
            outline.setOpacity(0.5);
        }
    }

    @FXML
    private void unhighlightIsland() {
        if(outline.getOpacity() == 0.5) {
            outline.setOpacity(0);
        }
    }

    @FXML
    public void setIslandNumber(int number) {
        labelIslandCountAndSelected.setText(String.valueOf(number));
    }

    @FXML
    public void setIslandImage(int number) {
        switch(number) {
            case 1,4,7,10 -> islandBackground.setImage(new Image(this.getClass().getResource("/assets/table/island1.png").toExternalForm()));
            case 2,5,8,11 -> islandBackground.setImage(new Image(this.getClass().getResource("/assets/table/island2.png").toExternalForm()));
            case 3,6,9,12 -> islandBackground.setImage(new Image(this.getClass().getResource("/assets/table/island3.png").toExternalForm()));
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void setStudents(int number) {
        List<Student> students = Client.getInstance().getGameHandler().getGame().getIslands().get(number-1).getStudents();
        List<PawnColor> colors = new ArrayList<>(); //all student colors on the island are saved in order
        List<PawnColor> colorsPosition = new ArrayList<>(); //every color of the students on the island is saved (only once), which is useful for knowing the location where it is printed
        for(int i=0; i<students.size(); i++) {

            if(colorsPosition.contains(students.get(i).getColor())) {
                getGUILabelStudent(colorsPosition.indexOf(students.get(i).getColor()) + 1).setText(Integer.toString(Collections.frequency(colors,students.get(i).getColor())) + 1);
                getGUILabelStudent(colorsPosition.indexOf(students.get(i).getColor()) + 1).setOpacity(1);
            }
            else {
                getFirstBlankStudentSpace().setImage(getStudentImage(students.get(i)));
                getFirstBlankStudentSpace().setOpacity(1);
            }

            if(students.size() > 5) {
                if(!colors.contains(students.get(i).getColor())) {
                    colorsPosition.add(students.get(i).getColor());
                }
                colors.add(students.get(i).getColor());
            }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private ImageView getFirstBlankStudentSpace() {
        ImageView student;
        for(int i=1; i<=5; i++) {
            student = getGUIStudent(i);
            if(student.getOpacity() == 0) {
                return student;
            }
        }
        return null;
    }

    /**
     * @author Christian Confalonieri
     */
    private ImageView getGUIStudent(int number) {
        return switch (number) {
            case 1 -> student1;
            case 2 -> student2;
            case 3 -> student3;
            case 4 -> student4;
            case 5 -> student5;
            default -> null;
        };
    }

    /**
     * @author Christian Confalonieri
     */
    private Label getGUILabelStudent(int number) {
        return switch (number) {
            case 1 -> labelStudent1;
            case 2 -> labelStudent2;
            case 3 -> labelStudent3;
            case 4 -> labelStudent4;
            case 5 -> labelStudent5;
            default -> null;
        };
    }

    /**
     * @author Christian Confalonieri
     */
    private Image getStudentImage(Student student) {
        return switch(student.getColor()) {
            case GREEN -> new Image(this.getClass().getResource("/assets/students/green.png").toExternalForm());
            case RED -> new Image(this.getClass().getResource("/assets/students/red.png").toExternalForm());
            case PINK -> new Image(this.getClass().getResource("/assets/students/pink.png").toExternalForm());
            case BLUE -> new Image(this.getClass().getResource("/assets/students/blue.png").toExternalForm());
            case YELLOW -> new Image(this.getClass().getResource("/assets/students/yellow.png").toExternalForm());
        };
    }
}
