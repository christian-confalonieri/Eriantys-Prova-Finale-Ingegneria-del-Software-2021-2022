package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Cloud;
import it.polimi.ingsw.model.entity.Student;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class GUICloudController {
    @FXML
    private AnchorPane cloudPane;
    @FXML
    private ImageView cloudBackground;
    @FXML
    private ImageView student1;
    @FXML
    private ImageView student2;
    @FXML
    private ImageView student3;
    @FXML
    private ImageView student4;
    @FXML
    private ImageView outline;

    private Cloud cloudModel;

    /**
     * @author Christian Confalonieri
     */
    protected void setCloudModel(Cloud cloudModel) {
        this.cloudModel = cloudModel;
    }

    /**
     * @author Christian Confalonieri
     */
    protected Cloud getCloudModel() {
        return cloudModel;
    }

    protected boolean isBoundToModel() { return !(cloudModel == null); }



    protected void render() {
        setStudents();
    }

    /**
     * @author Christian Confalonieri
     */
    public void remove() {
        cloudPane.setDisable(true);
        cloudPane.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void selectCloud() {
        if(outline.getOpacity()==0.5) {
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.addSelectedCloud(cloudModel));
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.selectCloud(outline));
        }
        else {
            Client.getInstance().getGui().guiCallGame(GUIGameController::removeSelectedCloud);
            outline.setOpacity(0.5);
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void highlightCloud() {
        if(outline.getOpacity() == 0) {
            outline.setOpacity(0.5);
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void unhighlightCloud() {
        if(outline.getOpacity() == 0.5) {
            unselectCloud();
        }
    }


    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void setCloudImage(int number) {
        switch (number) {
            case 1 -> cloudBackground.setImage(new Image(this.getClass().getResource("/assets/table/cloud1.png").toExternalForm()));
            case 2 -> cloudBackground.setImage(new Image(this.getClass().getResource("/assets/table/cloud2.png").toExternalForm()));
            case 3 -> cloudBackground.setImage(new Image(this.getClass().getResource("/assets/table/cloud3.png").toExternalForm()));
            case 4 -> cloudBackground.setImage(new Image(this.getClass().getResource("/assets/table/cloud4.png").toExternalForm()));
        }
    }

    /**
     * @author Christian Confalonieri
     */
    public void unselectCloud() {
        outline.setOpacity(0);
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void setStudents() {
        clearStudents();
        List<Student> students = cloudModel.getStudents();
        for(int i=1; i<= students.size(); i++) {
            getGUIStudent(i).setImage(getStudentImage(students.get(i-1)));
            getGUIStudent(i).setOpacity(1);
        }
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

    /**
     * @author Christian Confalonieri
     */
    private void clearStudents() {
        for(int i=1; i<=4; i++) {
            getGUIStudent(i).setOpacity(0);
        }
    }
}
