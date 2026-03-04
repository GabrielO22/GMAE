class InventoryItem {
    private String name;
    private int quantity;

    InventoryItem(String name, int initialQuantity) {
        this.name = name;
        this.quantity = initialQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    void adjustQuantity(int delta) {
        this.quantity += delta;
    }

    @Override
    public String toString() {
        return name;
    }
}
