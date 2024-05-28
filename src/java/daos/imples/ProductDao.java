/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.imples;



import daos.context.DBContext;
import models.Products;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends DBContext<Products> {

    public ProductDao() throws SQLException {
        super();
    }
    
    public List<Products> findByTitle(String title) {
    List<Products> products = new ArrayList<>();
    String sql = "SELECT * FROM products WHERE title LIKE ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, "%" + title + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Products product = new Products();
            product.setId(rs.getInt("id"));
            product.setTitle(rs.getString("title"));
            product.setDescription(rs.getString("description"));
            product.setThumbnail(rs.getString("thumbnail"));
            product.setCategory(rs.getString("category"));
            product.setOriginalPrice(rs.getDouble("original_price"));
            product.setSalePrice(rs.getDouble("sale_price"));
            product.setStock(rs.getInt("stock"));
            product.setCreatedAt(rs.getTimestamp("created_at"));
            product.setUpdatedAt(rs.getTimestamp("updated_at"));
            products.add(product);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return products;
}

     public List<Products> findByCategory(String category) {
        List<Products> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategory(rs.getString("category"));
                product.setOriginalPrice(rs.getDouble("original_price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setStock(rs.getInt("stock"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
     

    @Override
    public List<Products> findAll() {
        List<Products> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategory(rs.getString("category"));
                product.setOriginalPrice(rs.getDouble("original_price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setStock(rs.getInt("stock"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<String> findAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM products";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Products findById(int id) {
        Products product = null;
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new Products();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategory(rs.getString("category"));
                product.setOriginalPrice(rs.getDouble("original_price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setStock(rs.getInt("stock"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Products update(Products model) {
        String sql = "UPDATE products SET title = ?, description = ?, thumbnail = ?, category = ?, original_price = ?, sale_price = ?, stock = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, model.getTitle());
            ps.setString(2, model.getDescription());
            ps.setString(3, model.getThumbnail());
            ps.setString(4, model.getCategory());
            ps.setDouble(5, model.getOriginalPrice());
            ps.setDouble(6, model.getSalePrice());
            ps.setInt(7, model.getStock());
            ps.setInt(8, model.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public Products insert(Products model) {
        String sql = "INSERT INTO products (title, description, thumbnail, category, original_price, sale_price, stock, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, model.getTitle());
            ps.setString(2, model.getDescription());
            ps.setString(3, model.getThumbnail());
            ps.setString(4, model.getCategory());
            ps.setDouble(5, model.getOriginalPrice());
            ps.setDouble(6, model.getSalePrice());
            ps.setInt(7, model.getStock());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
