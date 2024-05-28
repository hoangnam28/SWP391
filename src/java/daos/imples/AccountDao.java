/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.imples;

import daos.context.DBContext;
import java.sql.SQLException;
import java.util.List;
import models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author admin
 */
public class AccountDao extends DBContext<User>{
    
    private static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM swp_391.users where email = ? and password = ?";

    public AccountDao() throws SQLException {
        super();
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public User login(String email,String password){
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFullName(rs.getString(2));
                user.setGender(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setAddress(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setStatus(rs.getString(9));
                return user;
            }
        } catch (SQLException e) {
        }
        return null;
    }
//      public static void main(String[] args) throws SQLException {
//            AccountDao dao = new AccountDao();
//            User u1 = dao.login("nam281002@gmail.com", "12345");
//            System.out.println(u1.getFullName());
//        }
}
