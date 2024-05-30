package daos.imples;

import daos.context.DBContext;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class UserDAO extends DBContext<User> {

    private static final String SELECT_ALL_POSTS_PAGINATED = "SELECT * FROM posts ORDER BY updated_at DESC LIMIT ?, ?";
    private static final String SELECT_TOTAL_POSTS = "SELECT COUNT(*) FROM posts";
    private static final String SELECT_POST_BY_ID = "SELECT * FROM posts WHERE id = ?";
    private static final String CURRENT_PASS = "SELECT id, password FROM users WHERE id = ? AND password = ?";
    private static final String CHANGE_PASS = "update users set password = ? where id = ?";

    public UserDAO() throws SQLException {
        super();
    }

    @Override
    public List<User> findAll() {

        return null;
    }

    @Override
    public User findById(int id) {
        User post = null;

        return post;
    }

    public boolean currentPass(int id, String oldpass) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CURRENT_PASS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, oldpass);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Check if the ResultSet has any rows (indicating a match)
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
            return false;
        }
    }

    public boolean changePass(int id, String newpass) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASS)) {
            preparedStatement.setString(1, newpass);
            preparedStatement.setInt(2, id);
            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.out.println("Fail: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User update(User model) {
        // Implementation for updating a post
        return null;
    }

    @Override
    public void deleteById(int id) {
        // Implementation for deleting a post
    }

    public int getTotalUsers() {
        int totalUsers = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOTAL_POSTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                totalUsers = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalUsers;
    }

    public static void main(String[] args) throws SQLException {
        UserDAO dao = new UserDAO();
        boolean check = dao.currentPass(1, "12345678");
//        boolean check = dao.changePass(1, "12345678");
        System.out.println(check);
    }

}
