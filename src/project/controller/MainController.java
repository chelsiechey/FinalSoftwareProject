package project.controller;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.event.ActionEvent;

public class MainController implements Initializable {
    private Stage addPartStage;  // Stage required to display application.
    private Parent scene;
    public void addPart(ActionEvent event) throws IOException {
        // Get event source from button
        addPartStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Load resources from view directory
        scene = FXMLLoader.load(getClass().getResource("/project/view/AddPart.fxml"));
        addPartStage.setTitle("Add Part");
        addPartStage.setScene(new Scene(scene));
        addPartStage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
