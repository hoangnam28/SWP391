package daos.imples;

import daos.context.DBContext;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import models.User;

public class UserDAO extends DBContext<User> {

    public UserDAO() throws SQLException {
        super();
    }

    public boolean changePass(int userId, String newPassword) {
        String query = "UPDATE swp_391.users SET password = ? WHERE id = ?";
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
        String query = "SELECT * FROM swp_391.users WHERE id = ? AND password = ?";
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User findById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User update(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
