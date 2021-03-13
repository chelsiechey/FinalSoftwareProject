package project.controller;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;
import project.model.Inventory;
import project.model.Part;
import project.model.Product;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.Optional;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.beans.binding.*;
import java.text.DecimalFormat;

/**
 * This class creates the MainController
 */
public class MainController implements Initializable {
    public TextField partSearchBar;
    public TextField productSearchBar;
    public Label partSearchResultInformationBar;
    public Label productSearchResultInformationBar;
    public Button addProductButton;
    private Parent scene;
    public static int uniquePartId;
    public static int uniqueProductId;
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
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Part, Integer> productIdColumn;
    @FXML
    private TableColumn<Part, String> productNameColumn;
    @FXML
    private TableColumn<Part, Double> productPriceColumn;
    @FXML
    private TableColumn<Part, Integer> productStockColumn;
    @FXML
    private TableColumn<Part, Integer> productMinStockColumn;
    @FXML
    private TableColumn<Part, Integer> productMaxStockColumn;

    // Part methods

    /**
     * This method launches the Add Part stage
     * @param actionEvent The ActionEvent object generated when the add part button is pressed
     * @throws IOException Throws an exception if the fxml file for the Add Part page is not found
     */
    public void addPart(ActionEvent actionEvent) throws IOException {
        Stage addPartStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/AddPart.fxml"));
        addPartStage.setTitle("Add Part");
        addPartStage.setScene(new Scene(scene));
        addPartStage.show();
    }

    /**
     * This method attempts to launch the Modify Part stage.
     * If there is not a part selected to modify the catch statement will execute.
     * This alerts the user that they must select a part to modify.
     * @param actionEvent The ActionEvent object generated when the modify part button is pressed
     * @throws IOException Throws an exception if the fxml filed for the Modify Part page is not found
     */
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/project/view/ModifyPart.fxml"));
            loader.load();

            // gets the ModifyPartController so the selected part can be passed
            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.getPartToModify(partTable.getSelectionModel().getSelectedItem());

            Stage modifyPartStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            modifyPartStage.setTitle("Modify Part");
            modifyPartStage.setScene(new Scene(scene));
            modifyPartStage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a part to modify");
            alert.showAndWait();
        }
    }

    /**
     * This method attempts to delete a part.
     * If there is not a part selected to delete the catch statement will execute.
     * This alerts the user that they must select a part to delete.
     * @param actionEvent The ActionEvent object generated when the delete part button is pressed
     */
    public void deletePart(ActionEvent actionEvent) {
        try {
            // This statement tries to look up the part.
            // If no part is found the catch statement will execute.
            Inventory.lookupPart(partTable.getSelectionModel().getSelectedItem().getId());
            // Confirms that the user would like to delete the part.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
                partTable.setItems(Inventory.getAllParts());
            }
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a part to delete");
            alert.showAndWait();
        }
    }

    /**
     * This method creates an ObservableList of parts to be displayed based on the user's search.
     * I originally struggled to make the search work for both strings and integers.
     * My original thought was to write a method in the Part model that took the string text input and
     * converted it to an integer, but this project specifies that the provided Part class cannot be
     * modified. After reviewing the textbook, I realized that the NumberFormatException can be used
     * to detect if a string is attempting to convert to an incompatible data type. I realized that the
     * catch statement could be used to call the Part class's overloaded lookup Part method as a string
     * and the try statement could use the same overloaded method for integers.
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

    // Product methods

     /**
     * This method launches the Add Product stage
     * @param actionEvent The ActionEvent object generated when the add product button is pressed
     * @throws IOException Throws an exception if the fxml file for the Add Product page is not found
     */
    public void addProduct(ActionEvent actionEvent) throws IOException {
        Stage addProductStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/AddProduct.fxml"));
        addProductStage.setTitle("Add Product");
        addProductStage.setScene(new Scene(scene));
        addProductStage.show();
    }

    /**
     * This method attempts to launch the Modify Product stage.
     * If there is not a product selected to modify the catch statement will execute.
     * This alerts the user that they must select a product to modify.
     * @param actionEvent The ActionEvent object generated when the modify product button is pressed
     * @throws IOException Throws an exception if the fxml filed for the Modify Product page is not found
     */
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/project/view/ModifyProduct.fxml"));
            loader.load();

            // gets the ModifyProductController so the selected product can be passed
            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.getProductToModify(productTable.getSelectionModel().getSelectedItem());

            Stage modifyProductStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            modifyProductStage.setTitle("Modify Product");
            modifyProductStage.setScene(new Scene(scene));
            modifyProductStage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a product to modify");
            alert.showAndWait();
        }
    }

    /**
     * This method attempts to delete a product.
     * If there is not a product selected to delete the catch statement will execute.
     * This alerts the user that they must select a product to delete.
     * If the product has any associated parts the user will get an error message informing
     * them that they must remove the associated parts before deleting.
     * @param actionEvent The ActionEvent object generated when the delete product button is pressed
     */
    public void deleteProduct(ActionEvent actionEvent) {
        try {
            // These statements try to look up the product by ID.
            // If no product is found the catch statement will execute.
            int id = productTable.getSelectionModel().getSelectedItem().getId();
            Inventory.lookupProduct(id);
            // Confirms that the user would like to delete the product.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // If statement checks to see if the product has any associated parts.
                // If it does not the product will be deleted.
                // If it does the user will get an error message informing them that they must remove
                // all associated parts before deleting.
                if (Inventory.lookupProduct(id).getAllAssociatedParts().size() > 0) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "All associated parts must be removed before deleting.");
                    alert1.showAndWait();
                } else {
                    Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                    productTable.setItems(Inventory.getAllProducts());
                }
            }
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a product to delete");
            alert.showAndWait();
        }
    }

    /**
     * This method creates an ObservableList of products to be displayed based on the user's search
     * @param keyEvent The KeyEvent object generated when a key is pressed in the product search bar
     */
    public void searchProducts(KeyEvent keyEvent) {
        // Stores the products that match the search criteria
        ObservableList<Product> productSearchResults = FXCollections.observableArrayList();
        try {
            // Gets the text from the search bar and attempts to convert it to an integer.
            // Catch statement executes if the value is not an integer.
            int searchInput = Integer.parseInt(productSearchBar.getText());
            Product intSearchResults = Inventory.lookupProduct(searchInput);
            productSearchResults.add(intSearchResults);
        }
        catch (NumberFormatException e) {
            String searchInput = productSearchBar.getText();
            productSearchResults.addAll(Inventory.lookupProduct(searchInput));
        }
        // Populates the Products table with only the products matching the search criteria
        productTable.setItems(productSearchResults);
        if (productSearchResults.size() > 1 && !productSearchBar.getText().equals("")) {
            productSearchResultInformationBar.setText("Showing " + productSearchResults.size() + " results.");
        }
        else if (productSearchResults.size() == 1 && !productSearchBar.getText().equals("")) {
            productTable.getSelectionModel().select(productSearchResults.get(0));
            productSearchResultInformationBar.setText("Showing " + productSearchResults.size() + " results.");
        }
        else if (productSearchResults.size() == 0 && !productSearchBar.getText().equals("")) {
            productSearchResultInformationBar.setText("No search results found.");
        }
        else {
            productSearchResultInformationBar.setText("Search by product name or ID.");
            productTable.getSelectionModel().clearSelection();
            productTable.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * The initialize method sets the starting state for the main stage.
     * The method sets up the TableView for parts and products and gets the current items from the Inventory class.
     * @param url Unused parameter for a url
     * @param resourceBundle Unused parameter for a resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up part table
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partMinStockColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        partMaxStockColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
        // Set up product table
        productTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productMinStockColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        productMaxStockColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
    }

    /**
     * This method exits the application.
     * @param actionEvent The ActionEvent object generated when the exit button is pressed
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
