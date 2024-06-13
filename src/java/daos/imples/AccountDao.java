package daos.imples;

import daos.context.DBContext;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDao extends DBContext<User> {

    private static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM swp_391.users WHERE email = ? AND password = ?";

    public AccountDao() throws SQLException {
        super();
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

    public User login(String email, String password) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setGender(rs.getString("gender"));
                user.setEmail(rs.getString("email"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role")); // Ensure this is set
                user.setStatus(rs.getString("status"));
                user.setAvatar(rs.getString("avatar")); // If avatar is a field
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static void main(String[] args) throws SQLException {
//    AccountDao dao = new AccountDao();
//    User u1 = dao.login("quockhangkid@gmail.com", "123456");
//    if (u1 != null) {
//        System.out.println("User full name: " + u1.getFullName());
//        System.out.println("User role: " + u1.getRole());
//    } else {
//        System.out.println("Login failed");
//    }
//}
}

