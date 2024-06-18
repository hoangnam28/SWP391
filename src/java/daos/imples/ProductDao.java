package daos.imples;

import daos.context.DBContext;
import models.Products;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
                product.setCategoryId(rs.getInt("category_id"));
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

    public List<Products> findByCategory(int categoryId) {
        List<Products> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategoryId(rs.getInt("category_id"));
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
                product.setCategoryId(rs.getInt("category_id"));
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
        String sql = "SELECT name FROM categories";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("name"));
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
                product.setCategoryId(rs.getInt("category_id"));
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
        String sql = "UPDATE products SET title = ?, description = ?, thumbnail = ?, category_id = ?, original_price = ?, sale_price = ?, stock = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, model.getTitle());
            ps.setString(2, model.getDescription());
            ps.setString(3, model.getThumbnail());
            ps.setInt(4, model.getCategoryId());
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
    public List<Products> findByDescription(String description) {
        List<Products> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE LOWER(description) LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + description.toLowerCase() + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String desc = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                int categoryId = rs.getInt("category_id");
                double originalPrice = rs.getDouble("original_price");
                Double salePrice = rs.getDouble("sale_price");
                int stock = rs.getInt("stock");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");
                products.add(new Products(id, title, desc, thumbnail, categoryId, originalPrice, salePrice, stock, createdAt, updatedAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
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
        String sql = "INSERT INTO products (title, description, thumbnail, category_id, original_price, sale_price, stock, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, model.getTitle());
            ps.setString(2, model.getDescription());
            ps.setString(3, model.getThumbnail());
            ps.setInt(4, model.getCategoryId());
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
    public List<Products> getTopProducts(int limit) {
        List<Products> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY created_at DESC LIMIT ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategoryId(rs.getInt("category_id"));
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
    public List<Products> findByPriceOrder(String order) {
    List<Products> products = new ArrayList<>();
    String sql = "SELECT * FROM products ORDER BY sale_price " + order;
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Products product = new Products();
            product.setId(rs.getInt("id"));
            product.setTitle(rs.getString("title"));
            product.setDescription(rs.getString("description"));
            product.setThumbnail(rs.getString("thumbnail"));
            product.setCategoryId(rs.getInt("category_id"));
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
    public List<Products> findByCategoryWithPagination(int categoryId, int startIndex, int recordsPerPage) {
        List<Products> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ? LIMIT ?, ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, startIndex);
            ps.setInt(3, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategoryId(rs.getInt("category_id"));
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

    public int countProductsByCategory(int categoryId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS count FROM products WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Products> findAllWithPagination(int startIndex, int recordsPerPage) {
        List<Products> products = new ArrayList<>();
        String sql = "SELECT * FROM products LIMIT ?, ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, startIndex);
            ps.setInt(2, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategoryId(rs.getInt("category_id"));
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

    public int countAllProducts() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS count FROM products";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count
                        = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
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

    public List<Products> findLatestProducts() {
        List<Products> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY created_at DESC LIMIT 5";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setCategoryId(rs.getInt("category_id"));
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

}
