package project.model;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * This public class provides Inventory methods
 */
public class Inventory {
    // list of Part objects
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    // list of Product objects
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method adds a new part to the ObservableList array allParts
     * @param newPart The new Part object that will be added to the ObservableList array allParts
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a new product to the ObservableList array allProducts
     * @param newProduct The new Product object that will be added to the ObservableList array allProducts
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method returns the Part object with the matching ID
     * @param partId The ID for the Part object that is being looked up
     * @return Returns either the Part object with the matching ID or null if not found
     */
    public static Part lookupPart(int partId) {
        // FIX ME! Add code to find Part by partID
        System.out.println("Fix me - Inventory model. Add code to find partID.");
        return null;
    }

    /**
     * This method returns the Product object with the matching ID
     * @param productID The ID for the Product object that is being looked up
     * @return Returns either the Product object with the matching ID or null if not found
     */
    public static Product lookupProduct(int productID) {
        // FIX ME! Add code to find Product by productID
        System.out.println("Fix me - Inventory model. Add code to find productID.");
        return null;
    }
}
