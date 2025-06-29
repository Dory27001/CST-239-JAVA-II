package app;

public class SalableProduct implements Comparable<SalableProduct> {
    private String name;
    private String description;
    private double price;
    private int quantity;

    public SalableProduct(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return name + " - $" + price + " [" + quantity + " available]";
    }

    // Sorting by name (case-insensitive), then price
    @Override
    public int compareTo(SalableProduct other) {
        int nameComparison = this.name.compareToIgnoreCase(other.name);
        if (nameComparison != 0) {
            return nameComparison;
        }
        return Double.compare(this.price, other.price);
    }
}
