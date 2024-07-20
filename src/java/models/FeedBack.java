package models;

import java.sql.Timestamp;

public class FeedBack {

    private int id;
    private int userId;
    private int productId;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String userName; // New field for user's full name

    // Constructors
    public FeedBack() {
    }
 public FeedBack(int id, int userId, int productId, int rating, String comment, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        
    }
    public FeedBack(int id, int userId, int productId, int rating, String comment, Timestamp createdAt, Timestamp updatedAt, String userName) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userName = userName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Feedback{"
                + "id=" + id
                + ", userId=" + userId
                + ", productId=" + productId
                + ", rating=" + rating
                + ", comment='" + comment + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", userName='" + userName + '\''
                + '}';
    }
}