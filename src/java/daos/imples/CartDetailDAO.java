package daos.imples;

import daos.context.DBContext;
import models.CartItem;
import models.Products;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDetailDAO extends DBContext<CartItem> {

    public CartDetailDAO() throws SQLException {
        super();
    }

    public List<CartItem> listAll() throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart_items";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem cartItem = mapResultSetToCartItem(rs);
                cartItems.add(cartItem);
            }
        }
        return cartItems;
    }

    private CartItem mapResultSetToCartItem(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setId(rs.getInt("id"));
        cartItem.setCartId(rs.getInt("cart_id"));
        cartItem.setProductId(rs.getInt("product_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setPrice(rs.getDouble("price"));
        cartItem.setTotalPrice(rs.getDouble("total_price"));

        Products product = findProductById(cartItem.getProductId());
        cartItem.setProduct(product);

        return cartItem;
    }
    
    public List<CartItem> listAllByUser(int userid) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT ci.* FROM cart_items as Ci join carts as C on c.id = ci.cart_id where c.user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem cartItem = mapResultSetToCartItem(rs);
                cartItems.add(cartItem);
            }
        }catch(Exception e) {
            System.out.println("Error: " + e);
        }
        return cartItems;
    }

    private Products findProductById(int productId) throws SQLException {
        Products product = null;
        String sql = "SELECT p.id, p.thumbnail, p.description, p.category_id, p.sale_price "
                + "FROM products p "
                + "JOIN categories c ON p.category_id = c.id "
                + "WHERE p.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Products();
                    product.setId(rs.getInt("id"));
                    product.setThumbnail(rs.getString("thumbnail"));
                    product.setDescription(rs.getString("description"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setSalePrice(rs.getDouble("sale_price"));
                }
            }
        }
        return product;
    }

    public void updateCartItemQuantity(int cartItemId, int newQuantity) throws SQLException {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, cartItemId);
            ps.executeUpdate();
        }
    }

    public void deleteCartItemByProductId(int productId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    }
    public void deleteCartItemByCartID(int cartId, int productId) throws SQLException {
    String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, cartId);
        ps.setInt(2, productId);
        ps.executeUpdate();
    }
}

    public double calculateSubTotal(int cartId) throws SQLException {
        double subTotal = 0.0;
        String sql = "SELECT SUM(total_price) AS sub_total FROM cart_items WHERE cart_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    subTotal = rs.getDouble("sub_total");
                }
            }
        }
        return subTotal;
    }

    public String getProductDescription(int productId) throws SQLException {
        Products product = findProductById(productId);
        return product != null ? product.getDescription() : null;
    }

    public String getProductThumbnail(int productId) throws SQLException {
        Products product = findProductById(productId);
        return product != null ? product.getThumbnail() : null;
    }

    public int getCartItemQuantity(int cartItemId) throws SQLException {
        CartItem cartItem = findById(cartItemId);
        return cartItem != null ? cartItem.getQuantity() : 0;
    }

    public double getCartItemPrice(int cartItemId) throws SQLException {
        CartItem cartItem = findById(cartItemId);
        return cartItem != null ? cartItem.getPrice() : 0.0;
    }

    public double getCartItemTotalPrice(int cartItemId) throws SQLException {
        CartItem cartItem = findById(cartItemId);
        return cartItem != null ? cartItem.getTotalPrice() : 0.0;
    }

    @Override
    public List<CartItem> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CartItem update(CartItem model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws SQLException {
        try {
            CartDetailDAO cartDetailDAO = new CartDetailDAO();

//            int cartItemIdToUpdate = 5;
//            int newQuantity = 5;
//            System.out.println("Updating cart item with ID " + cartItemIdToUpdate + " to quantity " + newQuantity);
//            cartDetailDAO.updateCartItemQuantity(cartItemIdToUpdate, newQuantity);
            int cartItemIdToDelete = 9;
            System.out.println("Deleting cart item with ID " + cartItemIdToDelete);
            cartDetailDAO.deleteCartItemByProductId(cartItemIdToDelete);

            int cartId = 1;
            double subTotal = cartDetailDAO.calculateSubTotal(cartId);
            System.out.println("Subtotal for cart ID " + cartId + " is " + subTotal);

            List<CartItem> updatedCartItems = cartDetailDAO.listAll();
            System.out.println("List of Cart Items after update and delete operations:");
            for (CartItem cartItem : updatedCartItems) {
                System.out.println(cartItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CartItem findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}