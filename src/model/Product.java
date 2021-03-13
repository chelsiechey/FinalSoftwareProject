package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a product
 * @author Chelsie Conrad
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    // constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // setters
    /**
     * @param id The value set to the product's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name The value set to the product's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price The value to set to the product's price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock The value to set to the the product's stock (the number of the product available)
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min The value to set to the product's requirement for the minimum number to be kept in stock
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max The value to set to the product's maximum number of product items that the company can store
     */
    public void setMax(int max) {
        this.max = max;
    }

    // getters

    /**
     * @return Returns the product's ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns the product's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns the product's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return Returns the number of the product in stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return Returns the minimum number of the product required to be kept in stock
     */
    public int getMin() {
        return min;
    }

    /**
     * @return Returns the maximum number of the product the company is able to keep in stock
     */
    public int getMax() {
        return max;
    }

    // other methods
    /**
     * This method adds a Part object to the ObservableList array associatedParts
     * @param part The part to be added to the ObservableList array associatedParts
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * This method removes a Part object from the ObservableList array associatedParts
     * @param selectedAssociatedPart The part to be removed from the ObservableList array associatedParts
     * @return Returns true if the Part is removed
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method returns all Part objects in the ObservableList array associatedParts
     * @return Returns all Part objects in the ObservableList array associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}