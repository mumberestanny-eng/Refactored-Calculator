package mechanic;

public class Part {
    private final double price;
    private final String name;
    private final int stock;
    private final String supplier;
    private final String category;

    public Part(String name, String category, double price, int stock, String supplier) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.supplier = supplier;
        this.category = category;
    }

    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getStock() {return stock;}
    public String getSupplier() {return supplier;}
    public String getCategory() {return category;}
    @Override
    public String toString() {
        return String.format("%s (Price: $%.2f -- Stock: %d -- Category: %s -- Supplier: %s)",name,price,stock, category,supplier);
    }
}
