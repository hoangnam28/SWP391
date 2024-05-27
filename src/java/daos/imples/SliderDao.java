/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.imples;

import daos.context.DBContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Slider;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author admin
 */
public class SliderDao extends DBContext<Slider>{
    
    public SliderDao() throws SQLException {
        super();
    }

    @Override
    public List<Slider> findAll() {
        List<Slider> sliders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("Select * from sliders")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt(1));
                slider.setTitle(rs.getString(2));
                slider.setImage(rs.getString(3));
                slider.setBacklink(rs.getString(4));
                slider.setStatus(rs.getString(5));
                sliders.add(slider);
            }
            return sliders;
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public Slider findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Slider update(Slider model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from sliders where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void UpdateStatus(int id,String status) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("update sliders set status = ? where id = ?")) {
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
}
