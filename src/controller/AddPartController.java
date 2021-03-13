package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

/**
 * This class creates the AddPartController
 */
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

    /**
     * This method sets the machineIdOrOutsourcedLabel to Machine ID
     * @param actionEvent The ActionEvent object generated when the In-House radio button is selected.
     */
    public void inHouse(ActionEvent actionEvent) {
        machineIdOrOutsourcedLabel.setText("Machine ID");
    }

    /**
     * This method sets the machineIdOrOutsourcedLabel to Company Name
     * @param actionEvent The ActionEvent object generated when the Outsourced radio button is selected
     */
    public void outsourced(ActionEvent actionEvent) {
        machineIdOrOutsourcedLabel.setText("Company Name");
    }

    /**
     * This method determines if the minimum value is less than the maximum value.
     * @param min The minimum value
     * @param max The maximum value
     * @return Returns true if min is greater than or equal to max or false if it is less than max.
     * Checks to make sure minimum value should always be less than the maximum.
     */
    public boolean isMinMaxError(int min, int max) {
        return min >= max;
    }

    /**
     * This method determines if the stock is between the minimum and maximum values.
     * @param min The minimum value
     * @param max The maximum value
     * @param stock The number in stock (inventory)
     * @return Returns true if stock is greater than max or less than min. Otherwise returns false.
     * Checks to make sure that stock is always between the values of min and max.
     */
    public boolean isStockError(int min, int max, int stock) {
        if (stock > max) {
            return true;
        }
        else return stock < min;
    }

    /**
     * This method determines if the input is an integer.
     * @param input The String value that the method will attempt to convert to an integer.
     * @return Returns true if the String can be converted to an integer. Otherwise returns false.
     */
    public boolean isInteger (String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    /**
     * This method determines if the input is a double.
     * @param input The String value that the method will attempt to convert to an integer.
     * @return Returns true if the String can be converted to an integer. Otherwise returns false.
     */
    public boolean isDouble (String input) {
        try {
            Double.parseDouble(input);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    /**
     * This method creates a new Part object if there are no errors and then redirects to the main page.
     * It uses the methods isInteger and isDouble to check that the user entered the correct data type.
     * It uses the methods isMinMaxError and isStockError to determine if the values for min, max, and stock are appropriate.
     * If the In-House radio button is selected, an InHouse part is created.
     * If the Outsourced radio button is selected, an Outsourced part is created.
     * @param actionEvent The ActionEvent object generated when the save button is pressed.
     * @throws IOException Throws an exception if the fxml file for the Main page is not found
     */
    public void saveNewPart(ActionEvent actionEvent) throws IOException {
        boolean isMinInteger = isInteger(partMinTextField.getText());
        boolean isMaxInteger = isInteger(partMaxTextField.getText());
        boolean isStockInteger = isInteger(partStockTextField.getText());
        boolean isPriceDouble = isDouble(partPriceTextField.getText());
        boolean isMachineIdInteger = true;
        if (inHouseRadioButton.isSelected()) {
            isMachineIdInteger = isInteger(partMachineIdOrCompanyName.getText());
        }
        if ((!isMinInteger || !isMaxInteger || !isStockInteger || !isPriceDouble || !isMachineIdInteger) && inHouseRadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The stock, min, max, and machine ID fields must be integers. Price must be a double.");
            alert.showAndWait();
        }
        else if (!isMinInteger || !isMaxInteger || !isStockInteger || !isPriceDouble) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The stock, min, and max fields must be integers. Price must be a double.");
            alert.showAndWait();
        }
        else {
            int min = Integer.parseInt(partMinTextField.getText());
            int max = Integer.parseInt(partMaxTextField.getText());
            int stock = Integer.parseInt(partStockTextField.getText());
            boolean minMaxError = isMinMaxError(min, max);
            boolean stockError = isStockError(min, max, stock);
            if (minMaxError && stockError) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Min must be less than max, and stock must be between min and max.");
                alert.showAndWait();
            } else if (minMaxError) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Min must be less than max.");
                alert.showAndWait();
            } else if (stockError) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Stock must be between min and max.");
                alert.showAndWait();
            } else {
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
                scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    /**
     * This method brings the user back to the main page without saving the part.
     * @param actionEvent The ActionEvent object generated when the exit button is pressed.
     * @throws IOException Throws an exception if the fxml file for the Main page is not found
     */
    public void exitAddPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setTitle("Inventory System");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method initializes the starting value for the machineIdOrOutsourcedLabel to Machine ID
     * @param url Unused parameter for a url
     * @param resourceBundle Unused parameter for a resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        machineIdOrOutsourcedLabel.setText("Machine ID");
    }
}