/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.imples;

import daos.context.DBContext;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Cart;
import models.CartItem;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import models.Products;
import models.User;

/**
 *
 * @author NgocHung_ighoorbeos
 */
public class CartDAO extends DBContext<Cart> {

    public CartDAO() throws SQLException {
        super();
    }

    public void addCartItem(CartItem cartItem) {
        LocalDate curDate = java.time.LocalDate.now();
        String date = curDate.toString();
        try {
            CartItem i = cartItem;
            double totalPrice = i.getQuantity() * i.getPrice();
            String sql = "insert into cart_items (cart_id, product_id, quantity, price) values(?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cartItem.getCartId());
            st.setInt(2, cartItem.getProductId());
            st.setInt(3, i.getQuantity());
            st.setDouble(4, i.getPrice());
//            st.setDouble(5, totalPrice);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Add to cartItem 1123: " + e);
        }
    }

    public boolean updateCart(Cart cart) {
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
        try {
            String sql = "UPDATE carts SET total_cost = ?, updated_at = ? WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            int index = 1;
            st.setDouble(index++, cart.getTotalCost());
            st.setTimestamp(index++, updatedAt);
            st.setInt(index++, cart.getId());
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.out.println("Update cart: " + e);
        }
        return false;
    }

    public boolean updateCartItem(CartItem cartitem) {
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
        try {
            String sql = "UPDATE cart_items SET quantity=? WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            int index = 1;
            st.setInt(index++, cartitem.getQuantity());
            st.setInt(index++, cartitem.getId());
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.out.println("Update cart: " + e);
        }
        return false;
    }

    public int addCart(Cart cart) {
        Timestamp createAt = new Timestamp(System.currentTimeMillis());
        try {
            String sql = "insert into carts (user_id, total_cost, created_at) values(?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            st.setInt(index++, cart.getUserId());
            st.setDouble(index++, cart.getTotalCost());
            st.setTimestamp(index++, createAt);
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Add to cart: " + e);
        }
        return 0;
    }

    public Cart getCartWithByUser(int userId) {
        String query = "Select * from carts where user_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setUserId(userId);
                cart.setTotalCost(rs.getDouble("total_cost"));
                return cart;
            }
        } catch (SQLException e) {
            System.out.println("Error get: " + e);
        }
        return null;
    }
    

    public CartItem getCartItemsWithDetails(int productId, int userId) {
        String query = "Select * from cart_items as ci join carts c on c.id = ci.cart_id where c.user_id=? and ci.product_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, userId);
            st.setInt(2, productId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCartId(resultSet.getInt("cart_id"));
                cartItem.setId(resultSet.getInt("id"));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItem.setTotalPrice(resultSet.getDouble("total_price"));
                return cartItem;
            }
        } catch (SQLException e) {
            System.out.println("Error get cart item: " + e);
        }
        return null;
    }

    public List<CartItem> getCartItemsWithDetails(int cart_item_id) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT p.thumbnail, p.description, citem.quantity, citem.total_price "
                + "FROM cart_items citem "
                + "JOIN carts c ON citem.cart_id = c.id "
                + "JOIN products p ON citem.product_id = p.id "
                + "WHERE citem.cart_id = ?";  // corrected WHERE clause to use citem.cart_id

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cart_item_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CartItem cartItem = new CartItem();
                    cartItem.setQuantity(resultSet.getInt("quantity"));
                    cartItem.setTotalPrice(resultSet.getDouble("total_price"));

                    Products product = new Products();
                    product.setThumbnail(resultSet.getString("thumbnail"));
                    product.setDescription(resultSet.getString("description"));

                    cartItem.setProduct(product);

                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle or log the exception appropriately
        }
        return cartItems;
    }

    @Override
    public List<Cart> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cart findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cart update(Cart model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public List<Cart> getAllCart() throws SQLException {
        List<Cart> carts = new ArrayList<>();
        String query = "SELECT * FROM carts";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart cart = mapResultSetToCart(rs);
                carts.add(cart);
            }
        }
        return carts;
    }
    public Cart getAllCartByUser(int userId) throws SQLException {
        String query = "SELECT * FROM carts where user_id =?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart cart = mapResultSetToCart(rs);
                return cart;
            }
        }
        return null;
    }

    // Helper method to map ResultSet to Cart
    private Cart mapResultSetToCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getInt("id"));
        cart.setUserId(rs.getInt("user_id"));

        cart.setCreatedAt(rs.getTimestamp("created_at"));
        cart.setUpdatedAt(rs.getTimestamp("updated_at"));
        return cart;
    }

}
