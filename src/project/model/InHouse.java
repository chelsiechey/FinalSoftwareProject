package project.model;

public class InHouse extends Part {
    private int machineId;

    // constructor, uses the super statement to call the superclass constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    // setter

    /**
     * @param machineId The value to set to the machine's ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    // getter

    /**
     * @return Returns the machine's ID
     */
    public int getMachineId() {
        return machineId;
    }
}

