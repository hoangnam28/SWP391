package daos.imples;

import daos.context.DBContext;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartContactDAO extends DBContext<User> {

    public CartContactDAO() throws SQLException {
        super();
    }

    // Retrieve users by their ID
    public List<User> findAllByUserID(int userId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapRowToUser(resultSet));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error retrieving users for ID " + userId, ex);
        }

        return users;
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET full_name = ?, gender = ?, email = ?, mobile = ?, address = ?, password = ?, role = ?, status = ?, avatar = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getGender());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getMobile());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getRole());
            statement.setString(8, user.getStatus());
            statement.setString(9, user.getAvatar());
            statement.setInt(10, user.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0 ? user : null;
        } catch (SQLException ex) {
            throw new RuntimeException("Error updating user with ID " + user.getId(), ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error deleting user with ID " + id, ex);
        }
    }

    // Maps a ResultSet row to a User object
    private User mapRowToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setFullName(resultSet.getString("full_name"));
        user.setGender(resultSet.getString("gender"));
        user.setEmail(resultSet.getString("email"));
        user.setMobile(resultSet.getString("mobile"));
        user.setAddress(resultSet.getString("address"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
        user.setStatus(resultSet.getString("status"));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));
        user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        user.setAvatar(resultSet.getString("avatar"));
        return user;
    }

//    public static void main(String[] args) {
//        try {
//            CartContactDAO dao = new CartContactDAO();
//
//            // Example for retrieving users by ID
//            List<User> users = dao.findAllByUserID(1);
//            users.forEach(System.out::println);
//
//            // Example for updating an existing user
//            User userToUpdate = users.get(0);
//            userToUpdate.setFullName("Updated Name");
//            boolean updated = dao.update(userToUpdate) != null;
//            System.out.println("User updated: " + updated);
//
//            // Example for deleting a user
//            dao.deleteById(userToUpdate.getId());
//            System.out.println("User deleted");
//
//        } catch (SQLException ex) {
//            System.err.println("Database error: " + ex.getMessage());
//        }
//    }
    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Optional, based on your needs
    }

    @Override
    public User findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Optional, based on your needs
    }

    public void insertOrderDetails(int userId, String receiverName, String receiverGender, String receiverEmail, String receiverMobile, String receiverAddress) {
        String sql = "INSERT INTO orders_combined (user_id, receiver_name, receiver_gender, receiver_email, receiver_mobile, receiver_address, total_cost, status, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, receiverName);
            statement.setString(3, receiverGender);
            statement.setString(4, receiverEmail);
            statement.setString(5, receiverMobile);
            statement.setString(6, receiverAddress);
            // total_cost and status are set to NULL, created_at and updated_at are set to CURRENT_TIMESTAMP

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Order details inserted successfully.");
            } else {
                System.out.println("Failed to insert order details.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error inserting order details", ex);
        }
    }

    public static void main(String[] args) {
        try {
            // Create an instance of the DAO
            CartContactDAO dao = new CartContactDAO();

            // Sample data to insert
            int userId = 2; // Example user ID
            String receiverName = "John Doe";
            String receiverGender = "Male";
            String receiverEmail = "johndoe@example.com";
            String receiverMobile = "123-456-7890";
            String receiverAddress = "1234 Elm Street, Springfield, IL";

            // Call the method to insert order details
            dao.insertOrderDetails(userId, receiverName, receiverGender, receiverEmail, receiverMobile, receiverAddress);

            System.out.println("Order details inserted successfully.");

        } catch (SQLException ex) {
            System.err.println("Database error: " + ex.getMessage());
        }
    }

}
