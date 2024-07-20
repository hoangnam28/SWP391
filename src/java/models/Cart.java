/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author YOUR NAM
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private int id;
    private int userId;
    private double totalCost;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<CartItem> cart_item;

    // Constructors
    public Cart() {
        cart_item = new ArrayList<>();
    }

    public List<CartItem> getCart_item() {
        return cart_item;
    }

    public void setCart_item(List<CartItem> cart_item) {
        this.cart_item = cart_item;
    }

    public int getQuantityByID(int id) {
        return getCartItemByID(id).getQuantity();
    }

    private CartItem getCartItemByID(int id) {
        for (CartItem cartItem : cart_item) {
            if (cartItem.getProduct().getId() == id) {
                return cartItem;
            }
        }
        return null;
    }

    //add item vao cart 
    public void addCartItem(CartItem t) {
        if(getCartItemByID(t.getProduct().getId())!= null){
            CartItem m = getCartItemByID(t.getProduct().getId());
            m.setQuantity(m.getQuantity() + t.getQuantity());
        }else{
            cart_item.add(t);
        }
    }
    
    public void removeCartItem(int id){
        if(getCartItemByID(id)!= null){
            cart_item.remove(getCartItemByID(id));
        }
    }

    public double getTotalMoney(){
        double t = 0;
        for (CartItem cartItem : cart_item) {
            t+= (cartItem.getQuantity()*cartItem.getProduct().getSalePrice());
            
        }
        return t;
    }
    
    public Cart(int userId, double totalCost) {
        this.userId = userId;
        this.totalCost = totalCost;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
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
        return "Cart{"
                + "id=" + id
                + ", userId=" + userId
                + ", totalCost=" + totalCost
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + '}';
    }
}
