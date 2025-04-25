package model;

public class Product {
    private int productId;
    private String productName;
    private String category;
    private String description;
    private double price;
    private int stockQuantity;
    private String sku;
    private String productImages;
    private String dimensions;
    private double weight;
    private double ratingsAverage;

    // Constructors
    public Product() {}

    public Product(String productName, String category, String description, double price, 
                   int stockQuantity, String sku, String productImages, String dimensions, 
                   double weight, double ratingsAverage) {
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.sku = sku;
        this.productImages = productImages;
        this.dimensions = dimensions;
        this.weight = weight;
        this.ratingsAverage = ratingsAverage;
    }

    // Getters and Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getProductImages() { return productImages; }
    public void setProductImages(String productImages) { this.productImages = productImages; }
    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public double getRatingsAverage() { return ratingsAverage; }
    public void setRatingsAverage(double ratingsAverage) { this.ratingsAverage = ratingsAverage; }
}