/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order {

    private int id;
    private int userId;
    private String receiverName;
    private String receiverGender;
    private String receiverEmail;
    private String receiverMobile;
    private String receiverAddress;
    private BigDecimal totalCost;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String Notes;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String productTitle;
    private String productThumbnail;
    private int saleId;
    private String saleName;
    private Products productEnt;

    public Products getProductEnt() {
        return productEnt;
    }

    public void setProductEnt(Products productEnt) {
        this.productEnt = productEnt;
    }

    public Order() {
    }

    public Order(int id, int userId, String receiverName, String receiverGender, String receiverEmail, String receiverMobile, String receiverAddress, BigDecimal totalCost, String status, Timestamp createdAt, Timestamp updatedAt, String Notes, int quantity, BigDecimal price, BigDecimal totalPrice, String productTitle, String productThumbnail, int saleId, String saleName, List<OrderItem> orderItems, List<OrderAssignment> orderAssignments) {
        this.id = id;
        this.userId = userId;
        this.receiverName = receiverName;
        this.receiverGender = receiverGender;
        this.receiverEmail = receiverEmail;
        this.receiverMobile = receiverMobile;
        this.receiverAddress = receiverAddress;
        this.totalCost = totalCost;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.Notes = Notes;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.productTitle = productTitle;
        this.productThumbnail = productThumbnail;
        this.saleId = saleId;
        this.saleName = saleName;
        this.orderItems = orderItems;
        this.orderAssignments = orderAssignments;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(String productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }
    private List<OrderItem> orderItems;
    private List<OrderAssignment> orderAssignments; // New field

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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverGender() {
        return receiverGender;
    }

    public void setReceiverGender(String receiverGender) {
        this.receiverGender = receiverGender;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderAssignment> getOrderAssignments() {
        return orderAssignments;
    }

    public void setOrderAssignments(List<OrderAssignment> orderAssignments) {
        this.orderAssignments = orderAssignments;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId=" + userId + ", receiverName=" + receiverName + ", receiverGender=" + receiverGender + ", receiverEmail=" + receiverEmail + ", receiverMobile=" + receiverMobile + ", receiverAddress=" + receiverAddress + ", totalCost=" + totalCost + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", Notes=" + Notes + ", orderItems=" + orderItems + ", orderAssignments=" + orderAssignments + '}';
    }

}
