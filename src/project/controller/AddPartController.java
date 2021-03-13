package project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.model.InHouse;
import project.model.Inventory;
import project.model.Outsourced;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;


public class AddPartController implements Initializable {
    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    private Stage stage;
    private Parent scene;
    public TextField partPriceTextField;
    public TextField partNameTextField;
    public TextField partIdTextField;
    public TextField partStockTextField;
    public TextField partMinTextField;
    public TextField partMaxTextField;
    public TextField partMachineIdOrCompanyName;
    public Button saveNewPart;
    public Button exitAddPart;
    @FXML
    private Label machineIdOrOutsourcedLabel;

    public void inHouse(ActionEvent actionEvent) {
        machineIdOrOutsourcedLabel.setText("Machine ID");
    }

    public void outsourced(ActionEvent actionEvent) {
        machineIdOrOutsourcedLabel.setText("Company Name");
    }

    public boolean isMinMaxError(int min, int max) {
        if (min >= max) {
            return true;
        }
        return false;
    }
    public boolean isStockError(int min, int max, int stock) {
        if (stock > max) {
            return true;
        }
        else if (stock < min) {
            return true;
        }
        else {
            return false;
        }
    }

    public void saveNewPart(ActionEvent actionEvent) throws IOException {
        int min = Integer.parseInt(partMinTextField.getText());
        int max = Integer.parseInt(partMaxTextField.getText());
        int stock = Integer.parseInt(partStockTextField.getText());
        boolean minMaxError = isMinMaxError(min, max);
        boolean stockError = isStockError(min, max, stock);
        if (minMaxError && stockError) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Min must be less than max, and stock must be between min and max.");
            alert.showAndWait();
        }
        else if (minMaxError) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Min must be less than max.");
            alert.showAndWait();
        }
        else if (stockError) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Stock must be between min and max.");
            alert.showAndWait();
        }
        else {
            if (inHouseRadioButton.isSelected()) {
                Inventory.addPart(new InHouse(
                        ++MainController.uniquePartId,
                        partNameTextField.getText(),
                        Double.parseDouble(partPriceTextField.getText()),
                        stock,
                        min,
                        max,
                        Integer.parseInt(partMachineIdOrCompanyName.getText()))
                );
            } else {
                Inventory.addPart(new Outsourced(
                        ++MainController.uniquePartId,
                        partNameTextField.getText(),
                        Double.parseDouble(partPriceTextField.getText()),
                        Integer.parseInt(partStockTextField.getText()),
                        Integer.parseInt(partMinTextField.getText()),
                        Integer.parseInt(partMaxTextField.getText()),
                        partMachineIdOrCompanyName.getText())
                );
            }
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/project/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void exitAddPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/Main.fxml"));
        stage.setTitle("Inventory System");
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        machineIdOrOutsourcedLabel.setText("Machine ID");
    }
}
