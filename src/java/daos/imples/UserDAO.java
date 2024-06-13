package daos.imples;

import daos.context.DBContext;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext<User> {
    private int noOfRecords;

    public UserDAO() throws SQLException {
        super();
    }

    public boolean changePass(int userId, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean currentPass(int userId, String oldPassword) {
        String query = "SELECT * FROM users WHERE id = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, oldPassword);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(int id) {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapResultSetToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insert(User user) {
        String query = "INSERT INTO users (full_name, gender, email, mobile, address, password, role, status, created_at, updated_at, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getGender());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMobile());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getPassword());  // You should hash the password here
            preparedStatement.setString(7, user.getRole());
            preparedStatement.setString(8, user.getStatus());
            preparedStatement.setString(9, user.getAvatar());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsersByRole(String role, String search, String status, String sort, int page, int pageSize) {
        List<User> users = new ArrayList<>();
        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM users WHERE role = ? AND status LIKE ? AND (full_name LIKE ? OR email LIKE ? OR mobile LIKE ?) ORDER BY " + sort + " LIMIT ?, ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, role);
            preparedStatement.setString(2, (status == null || status.isEmpty()) ? "%" : status);
            preparedStatement.setString(3, "%" + (search == null ? "" : search) + "%");
            preparedStatement.setString(4, "%" + (search == null ? "" : search) + "%");
            preparedStatement.setString(5, "%" + (search == null ? "" : search) + "%");
            preparedStatement.setInt(6, (page - 1) * pageSize);
            preparedStatement.setInt(7, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
            resultSet.close();

            resultSet = preparedStatement.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next()) {
                this.noOfRecords = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int getTotalRecords() {
        return noOfRecords;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String fullName = resultSet.getString("full_name");
        String gender = resultSet.getString("gender");
        String email = resultSet.getString("email");
        String mobile = resultSet.getString("mobile");
        String address = resultSet.getString("address");
        String password = resultSet.getString("password");
        String role = resultSet.getString("role");
        String status = resultSet.getString("status");
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        String avatar = resultSet.getString("avatar");

        if (resultSet.wasNull()) {
            avatar = null;
        }

        return new User(id, fullName, gender, email, mobile, address, password, role, status, createdAt, updatedAt, avatar);
    }

     @Override
    public User update(User user) {
        String query = "UPDATE users SET full_name = ?, gender = ?, email = ?, mobile = ?, address = ?, role = ?, status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getGender());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMobile());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setString(7, user.getStatus());
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
