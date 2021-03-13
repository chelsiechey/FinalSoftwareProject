package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates the AddProductController
 */
public class AddProductController implements Initializable {
    public TextField productIdTextField;
    public TextField productNameTextField;
    public TextField productPriceTextField;
    public TextField productStockTextField;
    public TextField productMinTextField;
    public TextField productMaxTextField;
    public Label partSearchResultInformationBar;
    public TextField partSearchBar;
    public Button addAssociatedPartButton;
    public Button removeAssociatedPartButton;
    public Button saveNewProduct;
    public Button exitAddProduct;
    private Stage stage;
    private Parent scene;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableColumn<Part, Integer> partStockColumn;
    @FXML
    private TableColumn<Part, Integer> partMinStockColumn;
    @FXML
    private TableColumn<Part, Integer> partMaxStockColumn;

    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartStockColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartMinStockColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartMaxStockColumn;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This method brings the user back to the main page without saving the product.
     * @param actionEvent The ActionEvent object generated when the exit button is pressed.
     * @throws IOException Throws an exception if the fxml file for the Main page is not found
     */
    public void exitAddProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setTitle("Inventory System");
        stage.setScene(new Scene(scene));
        stage.show();
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
     * This method creates a new Product object if there are no errors and then redirects to the main page.
     * It uses the methods isInteger and isDouble to check that the user entered the correct data type.
     * It uses the methods isMinMaxError and isStockError to determine if the values for min, max, and stock are appropriate.
     * Any associated parts are saved the the Product.
     * @param actionEvent The ActionEvent object generated when the save button is pressed.
     * @throws IOException Throws an exception if the fxml file for the Main page is not found
     */
    public void saveNewProduct(ActionEvent actionEvent) throws IOException {
        boolean isMinInteger = isInteger(productMinTextField.getText());
        boolean isMaxInteger = isInteger(productMaxTextField.getText());
        boolean isStockInteger = isInteger(productStockTextField.getText());
        boolean isPriceDouble = isDouble(productPriceTextField.getText());

        if (!isMinInteger || !isMaxInteger || !isStockInteger || !isPriceDouble) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The stock, min, and max fields must be integers. Price must be a double.");
            alert.showAndWait();
        }
        else {
            int min = Integer.parseInt(productMinTextField.getText());
            int max = Integer.parseInt(productMaxTextField.getText());
            int stock = Integer.parseInt(productStockTextField.getText());
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
                int id = ++MainController.uniqueProductId;
                Inventory.addProduct(new Product(
                        id,
                        productNameTextField.getText(),
                        Double.parseDouble(productPriceTextField.getText()),
                        Integer.parseInt(productStockTextField.getText()),
                        Integer.parseInt(productMinTextField.getText()),
                        Integer.parseInt(productMaxTextField.getText())
                ));

                Product currentProduct = Inventory.lookupProduct(id);
                for (Part associatedPart : associatedParts) {
                    currentProduct.addAssociatedPart(associatedPart);
                }

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    /**
     * This method attempts to remove an associated part.
     * If there is not a part selected to remove the catch statement will execute.
     * This alerts the user that they must select a part to remove.
     * @param actionEvent The ActionEvent object generated when the Remove from Associated Parts button is pressed.
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        try {
            // This statement tries to look up the part.
            // If no part is found the catch statement will execute.
            Inventory.lookupPart(partTable.getSelectionModel().getSelectedItem().getId());
            // Confirms that the user would like to remove the part.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Remove part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(associatedPartsTable.getSelectionModel().getSelectedItem());
                associatedPartsTable.setItems(associatedParts);
            }
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select an associated part to remove");
            alert.showAndWait();
        }
    }

    /**
     * This method attempts to add an associated part.
     * If there is not a part selected to add the catch statement will execute.
     * This alerts the user that they must select a part to add.
     * If I were to update this application, I would like to allow the user to decide how many parts
     * would be required for the product. If multiple of the same part were selected, the parts
     * would stack and show a total. I would also add a modify field to the associated parts table
     * to allow the user to enter however many are needed instead of clicking the part and hitting add
     * multiple times.
     * @param actionEvent The ActionEvent object generated when the Add to Associated Parts button is pressed.
     */
    public void addAssociatedPart(ActionEvent actionEvent) {
        try {
            // This statement tries to look up the part.
            // If no part is found the catch statement will execute.
            Inventory.lookupPart(partTable.getSelectionModel().getSelectedItem().getId());
            // Associates the part and resets the items in the associated parts table
            associatedParts.add(partTable.getSelectionModel().getSelectedItem());
            associatedPartsTable.setItems(associatedParts);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a part to associate");
            alert.showAndWait();
        }
    }

    /**
     * This method creates an ObservableList of parts to be displayed based on the user's search
     * @param keyEvent The KeyEvent object generated when a key is pressed in the part search bar
     */
    public void searchParts(KeyEvent keyEvent) {
        // Stores the parts that match the search criteria
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
        try {
            // Gets the text from the search bar and attempts to convert it to an integer.
            // Catch statement executes if the value is not an integer.
            int searchInput = Integer.parseInt(partSearchBar.getText());
            Part intSearchResults = Inventory.lookupPart(searchInput);
            partSearchResults.add(intSearchResults);
        }
        catch (NumberFormatException e) {
            String searchInput = partSearchBar.getText();
            partSearchResults.addAll(Inventory.lookupPart(searchInput));
        }
        // Populates the Parts table with only the parts matching the search criteria
        partTable.setItems(partSearchResults);
        if (partSearchResults.size() > 1 && !partSearchBar.getText().equals("")) {
            partSearchResultInformationBar.setText("Showing " + partSearchResults.size() + " results.");
        }
        else if (partSearchResults.size() == 1 && !partSearchBar.getText().equals("")) {
            partTable.getSelectionModel().select(partSearchResults.get(0));
            partSearchResultInformationBar.setText("Showing " + partSearchResults.size() + " results.");
        }
        else if (partSearchResults.size() == 0 && !partSearchBar.getText().equals("")) {
            partSearchResultInformationBar.setText("No search results found.");
        }
        else {
            partSearchResultInformationBar.setText("Search by part name or ID.");
            partTable.getSelectionModel().clearSelection();
            partTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * The initialize method sets the starting state for the add product stage.
     * The method sets up the TableView for parts and associated parts and gets the current associated parts.
     * @param url Unused parameter for a url
     * @param resourceBundle Unused parameter for a resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partMinStockColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        partMaxStockColumn.setCellValueFactory(new PropertyValueFactory<>("max"));

        associatedPartsTable.setItems(associatedParts);
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartMinStockColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        associatedPartMaxStockColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
    }
}