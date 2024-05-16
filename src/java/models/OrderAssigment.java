/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author YOUR NAM
 */
public class OrderAssigment {
    private int id;
    private int orderId;
    private int saleId;

    // Constructors
    public OrderAssigment() {}

    public OrderAssigment(int orderId, int saleId) {
        this.orderId = orderId;
        this.saleId = saleId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    @Override
    public String toString() {
        return "OrderAssignment{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", saleId=" + saleId +
                '}';
    }
}
