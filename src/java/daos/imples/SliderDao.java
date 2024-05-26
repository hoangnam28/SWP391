package daos.imples;

import models.Slider;
import daos.context.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SliderDao extends DBContext<Slider> {

    public SliderDao() throws SQLException {
        super();
    }

    @Override
    public List<Slider> findAll() {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM sliders WHERE status = 'active'";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getString("status"));
                slider.setCreatedAt(rs.getTimestamp("created_at"));
                slider.setUpdatedAt(rs.getTimestamp("updated_at"));
                sliders.add(slider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sliders;
    }

    @Override
    public Slider findById(int id) {
        Slider slider = null;
        String sql = "SELECT * FROM sliders WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    slider = new Slider();
                    slider.setId(rs.getInt("id"));
                    slider.setTitle(rs.getString("title"));
                    slider.setImage(rs.getString("image"));
                    slider.setBacklink(rs.getString("backlink"));
                    slider.setStatus(rs.getString("status"));
                    slider.setCreatedAt(rs.getTimestamp("created_at"));
                    slider.setUpdatedAt(rs.getTimestamp("updated_at"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slider;
    }

    @Override
    public Slider update(Slider model) {
        String sql = "UPDATE sliders SET title = ?, image = ?, backlink = ?, status = ?, updated_at = NOW() WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, model.getTitle());
            stmt.setString(2, model.getImage());
            stmt.setString(3, model.getBacklink());
            stmt.setString(4, model.getStatus());
            stmt.setInt(5, model.getId());

            if (stmt.executeUpdate() > 0) {
                return model;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM sliders WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
