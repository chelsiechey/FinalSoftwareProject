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

import javafx.event.ActionEvent;

public class MainController implements Initializable {
    public TextField partSearchBar;
    public TextField productSearchBar;
    public Label partSearchResultInformationBar;
    public Label productSearchResultInformationBar;
    public Button addProductButton;
    private Stage addPartStage;
    private Stage addProductStage;
    private Stage modifyProductStage;
    private Stage modifyPartStage;
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

    public void addPart(ActionEvent event) throws IOException {
        addPartStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/AddPart.fxml"));
        addPartStage.setTitle("Add Part");
        addPartStage.setScene(new Scene(scene));
        addPartStage.show();
    }

    public void modifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/project/view/ModifyPart.fxml"));
            loader.load();
            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.getPartToModify(partTable.getSelectionModel().getSelectedItem());
            modifyPartStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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

    public void deletePart(ActionEvent event) {
        try {
            Inventory.lookupPart(partTable.getSelectionModel().getSelectedItem().getId());
            Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());

        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a part to delete");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partMinStockColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        partMaxStockColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
        productTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productMinStockColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        productMaxStockColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
    }

    public void addProduct(ActionEvent event) throws IOException {
        addProductStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/AddProduct.fxml"));
        addProductStage.setTitle("Add Product");
        addProductStage.setScene(new Scene(scene));
        addProductStage.show();
    }

    public void modifyProduct(ActionEvent event) throws IOException {
        modifyProductStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/ModifyProduct.fxml"));
        modifyProductStage.setTitle("Modify Product");
        modifyProductStage.setScene(new Scene(scene));
        modifyProductStage.show();
    }

    /**
     * Clicking the delete button removes the selected Product from the inventory
     * @param event The event object created by clicking the Delete button
     */
    public void deleteProduct(ActionEvent event) {
        // FIX ME
        try {
            Inventory.lookupProduct(productTable.getSelectionModel().getSelectedItem().getId());
            Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());

        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a product to delete");
            alert.showAndWait();
        }
    }


    public void searchParts(KeyEvent keyEvent) {
        ObservableList<Part> partSearchResults = Inventory.lookupPart(partSearchBar.getText());
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
        }
    }

    public void searchProducts(KeyEvent keyEvent) {
        ObservableList<Product> productSearchResults = Inventory.lookupProduct(productSearchBar.getText());
        productTable.setItems(productSearchResults);
        if (productSearchResults.size() > 1 && !productSearchBar.getText().equals("")) {
            productTable.getSelectionModel().clearSelection();
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
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
