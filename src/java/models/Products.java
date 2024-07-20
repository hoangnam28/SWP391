package models;

import java.sql.Timestamp;

public class Products {
    private int id;
    private String title;
    private String description;
    private String thumbnail;
    private int categoryId; // Changed to int to store category ID
    private double originalPrice;
    private Double salePrice;
    private int stock;
    private Timestamp createdAt;
    private Timestamp updatedAt;
     private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Constructors
    public Products() {}

    public Products(int id, String title, String description, String thumbnail, int categoryId, double originalPrice, Double salePrice, int stock, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.categoryId = categoryId; // Updated constructor
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Products(String title, String description, String thumbnail, int categoryId, double originalPrice, Double salePrice, int stock) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.categoryId = categoryId; // Updated constructor
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.stock = stock;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getCategoryId() { // Changed getter method
        return categoryId;
    }

    public void setCategoryId(int categoryId) { // Changed setter method
        this.categoryId = categoryId;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", categoryId=" + categoryId + // Updated toString method
                ", originalPrice=" + originalPrice +
                ", salePrice=" + salePrice +
                ", stock=" + stock +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
