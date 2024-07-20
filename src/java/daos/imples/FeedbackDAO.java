// FeedbackDao.java
package daos.imples;

import daos.context.DBContext;

import java.sql.*;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import models.FeedBack;
import models.User;

public class FeedbackDAO extends DBContext<FeedBack> {

    public FeedbackDAO() throws SQLException {
        super();
    }

    public List<FeedBack> findByProductId(int id) throws SQLException {
    List<FeedBack> feedbacks = new ArrayList<>();
  String sql = "SELECT f.*, u.full_name FROM swp_391.feedback f JOIN swp_391.users u ON f.user_id = u.id WHERE f.product_id = ?";


    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            FeedBack feedback = new FeedBack();
            feedback.setId(rs.getInt("id"));
            feedback.setUserId(rs.getInt("user_id"));
            feedback.setProductId(rs.getInt("product_id"));
            feedback.setRating(rs.getInt("rating"));
            feedback.setComment(rs.getString("comment"));
            feedback.setCreatedAt(rs.getTimestamp("created_at"));
           
            feedback.setUserName(rs.getString("full_name")); // Set the user's full name
            feedbacks.add(feedback);
        }
    }
    return feedbacks;
}

 public int getTotalFeedback(int productId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM swp_391.feedback WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    @Override
    public List<FeedBack> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public FeedBack findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public FeedBack update(FeedBack model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}