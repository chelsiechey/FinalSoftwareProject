package project.model;

public class Outsourced extends Part {
    private String companyName;

    // constructor, uses the super statement to call the superclass constructor
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    // setter
    /**
     * @param companyName The value to set to the company's name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // getter

    /**
     * @return Return the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
