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


public class ModifyProductController implements Initializable {
    public Label partSearchResultInformationBar;
    public TextField partSearchBar;
    public Button addAssociatedPartButton;
    public Button removeAssociatedPartButton;
    public Button saveUpdatedProduct;
    public Button exitUpdatedProduct;
    private Stage stage;
    private Parent scene;
    @FXML
    private TextField productIdTextField;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productPriceTextField;
    @FXML
    private TextField productStockTextField;
    @FXML
    private TextField productMinTextField;
    @FXML
    private TextField productMaxTextField;
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
    public void exitUpdatedProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void saveUpdatedProduct(ActionEvent actionEvent) throws IOException {
        Product productToBeUpdated = Inventory.lookupProduct(Integer.parseInt(productIdTextField.getText()));
        int productToBeUpdatedIndex = Inventory.getAllProducts().indexOf(productToBeUpdated);
        Product updatedProduct;
        Inventory.updateProduct(productToBeUpdatedIndex, updatedProduct = new Product(
                Integer.parseInt(productIdTextField.getText()),
                productNameTextField.getText(),
                Double.parseDouble(productPriceTextField.getText()),
                Integer.parseInt(productStockTextField.getText()),
                Integer.parseInt(productMinTextField.getText()),
                Integer.parseInt(productMaxTextField.getText())
        ));

        for (Part associatedPart : associatedParts) {
            updatedProduct.addAssociatedPart(associatedPart);
        }

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void removeAssociatedPart(ActionEvent actionEvent) {
        try {
            Inventory.lookupPart(associatedPartsTable.getSelectionModel().getSelectedItem().getId());
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
        ObservableList<Part> partSearchResults = Inventory.lookupPart(partSearchBar.getText());
        partTable.setItems(partSearchResults);
        if (partSearchResults.size() > 1 && !partSearchBar.getText().equals("")) {
            partTable.getSelectionModel().clearSelection();
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

    public void getProductToModify(Product productToModify) {
        productIdTextField.setText(String.valueOf(productToModify.getId()));
        productNameTextField.setText(productToModify.getName());
        productPriceTextField.setText(String.valueOf(productToModify.getPrice()));
        productStockTextField.setText(String.valueOf(productToModify.getStock()));
        productMinTextField.setText(String.valueOf(productToModify.getMin()));
        productMaxTextField.setText(String.valueOf(productToModify.getMax()));

        for (Part part : productToModify.getAllAssociatedParts()) {
            associatedParts.add(part);
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
