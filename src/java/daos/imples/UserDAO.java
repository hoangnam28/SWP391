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

    public List<User> searchUsers(String search, String role) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ? AND (full_name LIKE ? OR email LIKE ? OR mobile LIKE ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, role);
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            preparedStatement.setString(4, "%" + search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Method for filter by status
    public List<User> filterUsersByStatus(String status, List<User> users) {
        if (status == null || status.isEmpty()) {
            return users;
        }
        List<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getStatus().equalsIgnoreCase(status)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    // Method for sort
    public List<User> sortUsers(String sortBy, List<User> users) {
        users.sort((u1, u2) -> {
            switch (sortBy) {
                case "email":
                    return u1.getEmail().compareToIgnoreCase(u2.getEmail());
                case "mobile":
                    return u1.getMobile().compareToIgnoreCase(u2.getMobile());
                default:
                    return u1.getFullName().compareToIgnoreCase(u2.getFullName());
            }
        });
        return users;
    }

    // Method for pagination
    public List<User> paginateUsers(List<User> users, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, users.size());
        this.noOfRecords = users.size();
        if (fromIndex > toIndex) {
            return new ArrayList<>();
        }
        return users.subList(fromIndex, toIndex);
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

        return new User(id, fullName, gender, email, mobile, address, password, role, status, avatar);
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

    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE users SET full_name = ?, gender = ?, mobile = ?, address = ?, avatar = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getGender());
            stmt.setString(3, user.getMobile());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getAvatar());
            stmt.setInt(6, user.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean verifyUser(String email) throws SQLException {
        String query = "UPDATE users SET status = 'Active' WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean checkUserExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean registerUser(User user) throws SQLException {
        String query = "INSERT INTO users (full_name, gender, email, mobile, address, password, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getGender());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getMobile());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getPassword());
            statement.setString(7, user.getRole());
            statement.setString(8, user.getStatus());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(int userId, String status) {
        String query = "UPDATE users SET status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<User> findAll1(String search, String status, String sort, int offset, int noOfRecords) {
        List<User> users = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS * FROM users WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            query.append(" AND (full_name LIKE ? OR email LIKE ? OR mobile LIKE ?)");
        }
        if (status != null && !status.isEmpty()) {
            query.append(" AND status = ?");
        }
        if (sort != null && !sort.isEmpty()) {
            query.append(" ORDER BY ").append(sort);
        }
        query.append(" LIMIT ").append(offset).append(", ").append(noOfRecords);

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
            resultSet.close();

            resultSet = ps.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }
    public List<User> findUsersByRole(String role) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ?";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, role);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.add(user);
            }
        }
        return users;
    }





}
