/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import daos.imples.CartDetailDAO;

/**
 *
 * @author HP
 */
public class GetNumberOfCart {

    public int getNumberOfCart(int userId) {
        try {
            CartDetailDAO cartDeatil = new CartDetailDAO();
            return cartDeatil.listAllByUser(userId).size();
        } catch (Exception e) {
            System.out.println("Get cart: " + e);
        }
        return 0;
    }
}
