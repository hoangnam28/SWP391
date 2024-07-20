package daos.imples;

import daos.context.DBContext;
import models.Order;
import models.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDao extends DBContext<Order> {

    public OrderDao() throws SQLException {
        super();
    }

    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Order findById(int id) {
        return findByIdOrder(id);
    }

    public Order findByIdOrder(int id) {
        String query = "SELECT o.*, " +
                "u.full_name AS customer_name, " +
                "oi.product_id, oi.quantity, oi.price, oi.total_price, " +
                "p.title AS product_title, p.thumbnail AS product_thumbnail, " +
                "su.full_name AS sale_user_name " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "JOIN order_items oi ON o.id = oi.order_id " +
                "JOIN products p ON oi.product_id = p.id " +
                "LEFT JOIN order_assignments oa ON o.id = oa.order_id " +
                "LEFT JOIN users su ON oa.sale_id = su.id " +
                "WHERE o.id = ?";

        Order order = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setReceiverName(rs.getString("receiver_name"));
                    order.setReceiverGender(rs.getString("receiver_gender"));
                    order.setReceiverEmail(rs.getString("receiver_email"));
                    order.setReceiverMobile(rs.getString("receiver_mobile"));
                    order.setReceiverAddress(rs.getString("receiver_address"));
                    order.setTotalCost(rs.getBigDecimal("total_cost"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));
                    order.setNotes(rs.getString("notes"));

                    List<OrderItem> items = new ArrayList<>();
                    do {
                        OrderItem item = new OrderItem();
                        item.setOrderId(rs.getInt("id"));
                        item.setProductId(rs.getInt("product_id"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setPrice(rs.getBigDecimal("price"));
                        item.setTotalPrice(rs.getBigDecimal("total_price"));

                        items.add(item);
                    } while (rs.next());
                    
                    order.setOrderItems(items);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public Order update(Order model) {
        String query = "UPDATE orders SET status = ?, notes = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, model.getStatus());
            ps.setString(2, model.getNotes());
            ps.setInt(3, model.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return model;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Order> getOrders(String sortField, String sortOrder, String fromDate, String toDate, String status, String search, int offset, int limit) {
        List<Order> orders = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT o.id AS order_id, o.*, u.full_name AS customer_name, "
                + "p.title AS product_title FROM orders o "
                + "JOIN users u ON o.user_id = u.id "
                + "JOIN order_items oi ON o.id = oi.order_id "
                + "JOIN products p ON oi.product_id = p.id "
                + "WHERE (o.created_at BETWEEN ? AND ?)");

        if (status != null && !status.isEmpty()) {
            queryBuilder.append(" AND o.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            queryBuilder.append(" AND (o.id LIKE ? OR u.full_name LIKE ?)");
        }

        queryBuilder.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder)
                .append(" LIMIT ? OFFSET ?");

        String query = queryBuilder.toString();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, fromDate != null ? fromDate : "1970-01-01");
            ps.setString(2, toDate != null ? toDate : "9999-12-31");

            int index = 3;
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            if (search != null && !search.isEmpty()) {
                ps.setString(index++, "%" + search + "%");
                ps.setString(index++, "%" + search + "%");
            }

            ps.setInt(index++, limit);
            ps.setInt(index, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setReceiverName(rs.getString("customer_name"));
                    order.setReceiverGender(rs.getString("receiver_gender"));
                    order.setReceiverEmail(rs.getString("receiver_email"));
                    order.setReceiverMobile(rs.getString("receiver_mobile"));
                    order.setReceiverAddress(rs.getString("receiver_address"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));

                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public int getOrderCount(String fromDate, String toDate, String status, String search) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM orders o "
                + "JOIN users u ON o.user_id = u.id "
                + "WHERE (o.created_at BETWEEN ? AND ?)");

        if (status != null && !status.isEmpty()) {
            queryBuilder.append(" AND o.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            queryBuilder.append(" AND (o.id LIKE ? OR u.full_name LIKE ?)");
        }

        String query = queryBuilder.toString();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, fromDate != null ? fromDate : "1970-01-01");
            ps.setString(2, toDate != null ? toDate : "9999-12-31");

            int index = 3;
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            if (search != null && !search.isEmpty()) {
                ps.setString(index++, "%" + search + "%");
                ps.setString(index++, "%" + search + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                // Add additional mappings as necessary
                orderItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createOrder(Order order) {
    String query = "INSERT INTO orders (user_id, total_cost, status, created_at, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
    try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, order.getUserId());
        ps.setBigDecimal(2, order.getTotalCost());
        ps.setString(3, order.getStatus() != null ? order.getStatus() : "Submitted"); // Add a default status here
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            order.setId(rs.getInt(1));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void createOrderItem(int orderId, int productId, int quantity, double price) {
    String query = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, orderId);
        ps.setInt(2, productId);
        ps.setInt(3, quantity);
        ps.setDouble(4, price); // Include price
        ps.executeUpdate();
    } catch (SQLException e) {
        Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, "Error creating order item: " + e.getMessage());
        e.printStackTrace();
    }
}


    public void assignOrderToSaler(int orderId, int salerId) {
        String query = "INSERT INTO order_assignments (order_id, sale_id) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE sale_id = VALUES(sale_id), updated_at = CURRENT_TIMESTAMP";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, salerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductQuantity(int productId, int quantityChange) {
        String query = "UPDATE products SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, quantityChange);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
