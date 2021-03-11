package project.controller;

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
import project.model.Inventory;
import project.model.Part;
import project.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


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
    public void exitAddProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/Main.fxml"));
        stage.setTitle("Inventory System");
        stage.setScene(new Scene(scene));
        stage.show();
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

    public void saveNewProduct(ActionEvent actionEvent) throws IOException {
        int min = Integer.parseInt(productMinTextField.getText());
        int max = Integer.parseInt(productMaxTextField.getText());
        int stock = Integer.parseInt(productStockTextField.getText());
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
            scene = FXMLLoader.load(getClass().getResource("/project/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void removeAssociatedPart(ActionEvent actionEvent) {
        try {
            Inventory.lookupPart(partTable.getSelectionModel().getSelectedItem().getId());
            associatedParts.remove(associatedPartsTable.getSelectionModel().getSelectedItem());
            associatedPartsTable.setItems(associatedParts);
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select an associated part to remove");
            alert.showAndWait();
        }
    }

    public void addAssociatedPart(ActionEvent actionEvent) {
        try {
            Inventory.lookupPart(partTable.getSelectionModel().getSelectedItem().getId());
            associatedParts.add(partTable.getSelectionModel().getSelectedItem());
            associatedPartsTable.setItems(associatedParts);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Select a part to associate");
            alert.showAndWait();
        }
    }

    public void searchParts(KeyEvent keyEvent) {
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
        try {
            int searchInput = Integer.parseInt(partSearchBar.getText());
            Part intSearchResults = Inventory.lookupPart(searchInput);
            partSearchResults.add(intSearchResults);
        }
        catch (NumberFormatException e) {
            String searchInput = partSearchBar.getText();
            partSearchResults.addAll(Inventory.lookupPart(searchInput));
        }
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
