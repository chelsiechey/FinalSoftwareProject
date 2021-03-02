package project.model;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Represents an inventory
 * @author Chelsie Conrad
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
        for (int index = 0; index < Inventory.getAllParts().size(); index++) {
            Part currentPart = Inventory.getAllParts().get(index);

            // Returns matching part
            if (currentPart.getId() == partId) {
                return currentPart;
            }
        }
        return null;
    }

    /**
     * This method returns the Product object with the matching ID
     * @param productId The ID for the Product object that is being looked up
     * @return Returns either the Product object with the matching ID or null if not found
     */
    public static Product lookupProduct(int productId) {
        for (int index = 0; index < Inventory.getAllProducts().size(); index++) {
            Product currentProduct = Inventory.getAllProducts().get(index);

            // Returns matching product
            if (currentProduct.getId() == productId) {
                return currentProduct;
            }
        }
        return null;
    }

    /**
     * This method returns the Part with the matching name
     * @param partName The name for the Part object that is being looked up
     * @return Returns either the Part object with the matching name or null if not found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsContainingSubstring = FXCollections.observableArrayList();

        // The for loop variable to the left of the colon is a temporary variable containing a single element from the collection on the right
        // With each iteration through the loop, Java pulls the next element from the collection and assigns it to the temp variable.
        for (Part currentPart : Inventory.getAllParts()) {
            if (currentPart.getName().contains(partName)) {
                partsContainingSubstring.add(currentPart);
            }
        }
        return partsContainingSubstring;
    }

    /**
     * The method returns the Product with the matching name
     * @param productName The name for the Product object that is being looked up
     * @return Returns either the Product object with the matching name or null if not found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsContainingSubstring = FXCollections.observableArrayList();

        // The for loop variable to the left of the colon is a temporary variable containing a single element from the collection on the right
        // With each iteration through the loop, Java pulls the next element from the collection and assigns it to the temp variable.
        for (Product currentProduct : Inventory.getAllProducts()) {
            if (currentProduct.getName().contains(productName)) {
                productsContainingSubstring.add(currentProduct);
            }
        }
        return productsContainingSubstring;
    }

    /**
     * This method updates the Part at the given index
     * @param index The index for the Part to be updated in the ObservableList
     * @param selectedPart The new Part object that will replace the current Part object at the given index
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method updates the Product at the given index
     * @param index The index for the Product to be updated in the ObservableList
     * @param newProduct The new Product object that will replace the current Product object at the given index
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * This method searches the Inventory and deletes the Part with the same ID as the selected Part
     * @param selectedPart The Part object being deleted
     * @return Returns true if the Part is deleted from the Observable List and returns false if the Part is not found
     */
    public static boolean deletePart(Part selectedPart) {
        // The for loop variable to the left of the colon is a temporary variable containing a single element from the collection on the right
        // With each iteration through the loop, Java pulls the next element from the collection and assigns it to the temp variable.
        for (Part currentPart : Inventory.getAllParts()) {
            if (currentPart.getId() == selectedPart.getId()) {
                return Inventory.getAllParts().remove(currentPart);
            }
        }
        return false;
    }

    /**
     * This method searches the Inventory and deletes the Product with the same ID as the selected Product
     * @param selectedProduct The Product object being deleted
     * @return Returns true if the Product is deleted from the ObservableList and returns false if the Products is not found
     */
    public static boolean deletedProduct(Product selectedProduct) {
        // The for loop variable to the left of the colon is a temporary variable containing a single element from the collection on the right
        // With each iteration through the loop, Java pulls the next element from the collection and assigns it to the temp variable.
        for (Product currentProduct : Inventory.getAllProducts()) {
            if (currentProduct.getId() == selectedProduct.getId()) {
                return Inventory.getAllProducts().remove(currentProduct);
            }
        }
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
