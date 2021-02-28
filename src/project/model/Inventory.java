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
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            Part currentPart = Inventory.getAllParts().get(i);

            // Returns first match
            if (currentPart.getId() == partId)
                return currentPart;
        }
        return null;
    }

    /**
     * This method returns the Product object with the matching ID
     * @param productID The ID for the Product object that is being looked up
     * @return Returns either the Product object with the matching ID or null if not found
     */
    public static Product lookupProduct(int productID) {
        // FIX ME! Add code to find Product by productID
        System.out.println("Fix me - Inventory model. Add code to find Product by productID.");
        return null;
    }

    /**
     * This method returns the Part with the matching name
     * @param partName The name for the Part object that is being looked up
     * @return Returns either the Part object with the matching name or null if not found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        // FIX ME! Add code to find Part by partName
        System.out.println("Fix me - Inventory model. Add code to find Part by partName.");
        return null;
    }

    /**
     * The method returns the Product with the matching name
     * @param productName The name for the Product object that is being looked up
     * @return Returns either the Product object with the matching name or null if not found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        // FIX ME! Add code to find Product by productName
        System.out.println("Fix me - Inventory model. Add code to find Product by productName.");
        return null;
    }

    /**
     * This method updates the Part at the given index
     * @param index The index for the Part to be updated in the ObservableList
     * @param selectedPart The new Part object that will replace the current Part object at the given index
     */
    public static void updatePart(int index, Part selectedPart) {
        // FIX ME! Add code to update selected Part at index
        System.out.println("Fix me - Inventory model. Add code to update the selected Part at the given index.");
    }

    /**
     * This method updates the Product at the given index
     * @param index The index for the Product to be updated in the ObservableList
     * @param newProduct The new Product object that will replace the current Product object at the given index
     */
    public static void updateProduct(int index, Product newProduct) {
        // FIX ME! Add code to updated selected Product at index
        System.out.println("Fix me - Inventory model. Add code to update the selected Product at the given index.");
    }

    /**
     * This method searches the Inventory and deletes the Part with the same ID as the selected Part
     * @param selectedPart The Part object being deleted
     * @return Returns true if the Part is deleted from the Observable List and returns false if the Part is not found
     */
    public static boolean deletePart(Part selectedPart) {
        // FIX ME! Add code to delete the selected Part from the ObservableList
        return false;
    }

    /**
     * This method searches the Inventory and deletes the Product with the same ID as the selected Product
     * @param selectedProduct The Product object being deleted
     * @return Returns true if the Product is deleted from the ObservableList and returns false if the Products is not found
     */
    public static boolean deletedProduct(Product selectedProduct) {
        // FIX ME! Add code to delete the selected Product from the ObservableList
        return false;
    }

    /**
     * This method returns and ObservableList with all of the Part objects.
     * @return Returns and ObservableList with all of the Part objects
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
