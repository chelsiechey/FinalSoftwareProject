package project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.model.InHouse;
import project.model.Inventory;
import project.model.Outsourced;
import project.model.Part;
import java.io.IOException;


public class ModifyPartController {
    public RadioButton modifyInHouseRadioButton;
    public RadioButton modifyOutsourcedRadioButton;
    public Button saveUpdatedPart;
    public Button exitModifyPart;
    public ToggleGroup inHouseOrOutsourcedToggleGroup;
    private Stage stage;
    private Parent scene;
    public TextField partPriceTextField;
    public TextField partNameTextField;
    public TextField partIdTextField;
    public TextField partInvTextField;
    public TextField partMinTextField;
    public TextField partMaxTextField;
    public TextField partMachineIdOrCompanyName;

    @FXML
    private Label machineIdOrOutsourcedLabel;
    @FXML
    private TableView<Part> partTable;

    public void inHouse(ActionEvent actionEvent) {
        machineIdOrOutsourcedLabel.setText("Machine ID");
    }

    public void outsourced(ActionEvent actionEvent) {
        machineIdOrOutsourcedLabel.setText("Company Name");
    }

    public void saveUpdatedPart(ActionEvent actionEvent) throws IOException {
        // Need to get the ID from the text field instead of generating it
        Part partToBeUpdated = Inventory.lookupPart(Integer.parseInt(partIdTextField.getText()));
        int partToBeUpdatedIndex = Inventory.getAllParts().indexOf(partToBeUpdated);
        if (modifyInHouseRadioButton.isSelected()) {
            Inventory.updatePart(partToBeUpdatedIndex, new InHouse(
                    Integer.parseInt(partIdTextField.getText()),
                    partNameTextField.getText(),
                    Double.parseDouble(partPriceTextField.getText()),
                    Integer.parseInt(partInvTextField.getText()),
                    Integer.parseInt(partMinTextField.getText()),
                    Integer.parseInt(partMaxTextField.getText()),
                    Integer.parseInt(partMachineIdOrCompanyName.getText()))
            );
        } else {
            Inventory.updatePart(partToBeUpdatedIndex, new Outsourced(
                   Integer.parseInt(partIdTextField.getText()),
                    partNameTextField.getText(),
                    Double.parseDouble(partPriceTextField.getText()),
                    Integer.parseInt(partInvTextField.getText()),
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

    public void exitModifyPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void getPartToModify(Part partToModify) {
        partIdTextField.setText(String.valueOf(partToModify.getId()));
        partNameTextField.setText(partToModify.getName());
        partPriceTextField.setText(String.valueOf(partToModify.getPrice()));
        partInvTextField.setText(String.valueOf(partToModify.getStock()));
        partMinTextField.setText(String.valueOf(partToModify.getMin()));
        partMaxTextField.setText(String.valueOf(partToModify.getMax()));
        if (partToModify instanceof InHouse) {
            modifyInHouseRadioButton.setSelected(true);
            machineIdOrOutsourcedLabel.setText("Machine ID");
            partMachineIdOrCompanyName.setText(String.valueOf(((InHouse)partToModify).getMachineId()));
        }
        else {
            modifyOutsourcedRadioButton.setSelected(true);
            machineIdOrOutsourcedLabel.setText("Outsourced");
            partMachineIdOrCompanyName.setText(String.valueOf(((Outsourced)partToModify).getCompanyName()));
        }
    }
}
