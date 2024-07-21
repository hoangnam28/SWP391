package daos.imples;

import daos.context.DBContext;
import java.math.BigDecimal;
import models.Order;
import models.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Products;

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
        String query = "SELECT o.*, " +
               "oi.quantity, oi.price, oi.total_price, " +
               "p.title AS product_title, p.thumbnail AS product_thumbnail, " +
               "oa.sale_id, " +
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
                    BigDecimal totalCost = rs.getBigDecimal("total_cost");
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setReceiverName(rs.getString("receiver_name"));
                    order.setReceiverGender(rs.getString("receiver_gender"));
                    order.setReceiverEmail(rs.getString("receiver_email"));
                    order.setReceiverMobile(rs.getString("receiver_mobile"));
                    order.setReceiverAddress(rs.getString("receiver_address"));
                    order.setTotalCost(totalCost);
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));
                    order.setNotes(rs.getString("notes"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setPrice(rs.getBigDecimal("price"));
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setProductTitle(rs.getString("product_title"));
                    order.setProductThumbnail(rs.getString("product_thumbnail"));
                    order.setSaleId(rs.getInt("sale_id"));
                    order.setSaleName(rs.getString("sale_user_name"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
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
    String query1 = "UPDATE orders SET status = ?, notes = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
    String query2 = "UPDATE order_assignments SET sale_id = ? WHERE order_id = ?";

    Connection conn = null;
    try {
        conn = connection; // Giả sử 'connection' là kết nối cơ sở dữ liệu của bạn
        conn.setAutoCommit(false);

        try (PreparedStatement ps1 = conn.prepareStatement(query1);
             PreparedStatement ps2 = conn.prepareStatement(query2)) {
            
            // Cập nhật đầu tiên
            ps1.setString(1, model.getStatus());
            ps1.setString(2, model.getNotes());
            ps1.setInt(3, model.getId());
            int rowsAffected1 = ps1.executeUpdate();

            // Cập nhật thứ hai
            ps2.setInt(1, model.getSaleId()); // Giả sử bạn có phương thức getSaleId()
            ps2.setInt(2, model.getId());
            int rowsAffected2 = ps2.executeUpdate();

            conn.commit();
            
            if (rowsAffected1 > 0 || rowsAffected2 > 0) {
                return model;
            } else {
                return null; // Không có bản ghi nào được cập nhật
            }
        }
    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return null;
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Order> getOrders(String sortField, String sortOrder, String fromDate, String toDate, String status, String search, int offset, int limit) {
    List<Order> orders = new ArrayList<>();

    StringBuilder queryBuilder = new StringBuilder("SELECT o.*, u.full_name AS customer_name, p.title AS product_title, p.thumbnail AS product_thumbnail " +
                                                   "FROM orders o " +
                                                   "JOIN users u ON o.user_id = u.id " +
                                                   "JOIN order_items oi ON o.id = oi.order_id " +
                                                   "JOIN products p ON oi.product_id = p.id " +
                                                   "WHERE 1=1");

    if (fromDate != null && !fromDate.isEmpty()) {
        queryBuilder.append(" AND o.created_at >= ?");
    }
    if (toDate != null && !toDate.isEmpty()) {
        queryBuilder.append(" AND o.created_at <= ?");
    }
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
        int index = 1;
        if (fromDate != null && !fromDate.isEmpty()) {
            ps.setString(index++, fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            ps.setString(index++, toDate);
        }
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
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setReceiverName(rs.getString("customer_name"));
                order.setReceiverGender(rs.getString("receiver_gender"));
                order.setReceiverEmail(rs.getString("receiver_email"));
                order.setReceiverMobile(rs.getString("receiver_mobile"));
                order.setReceiverAddress(rs.getString("receiver_address"));
                order.setTotalCost(rs.getBigDecimal("total_cost"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setProductTitle(rs.getString("product_title"));
                order.setProductThumbnail(rs.getString("product_thumbnail"));

                orders.add(order);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orders;
}
public int getOrderCount(String fromDate, String toDate, String status, String search) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM orders o " +
                                                       "JOIN users u ON o.user_id = u.id " +
                                                       "WHERE 1=1");

        if (fromDate != null && !fromDate.isEmpty()) {
            queryBuilder.append(" AND o.created_at >= ?");
        }
        if (toDate != null && !toDate.isEmpty()) {
            queryBuilder.append(" AND o.created_at <= ?");
        }
        if (status != null && !status.isEmpty()) {
            queryBuilder.append(" AND o.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            queryBuilder.append(" AND (o.id LIKE ? OR u.full_name LIKE ?)");
        }

        String query = queryBuilder.toString();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(index++, fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(index++, toDate);
            }
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
    String query = "INSERT INTO orders (user_id, receiver_name, receiver_gender, receiver_email, receiver_mobile, receiver_address, total_cost, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
    try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, order.getUserId());
        ps.setString(2, order.getReceiverName());
        ps.setString(3, order.getReceiverGender());
        ps.setString(4, order.getReceiverEmail());
        ps.setString(5, order.getReceiverMobile());
        ps.setString(6, order.getReceiverAddress());
        ps.setBigDecimal(7, order.getTotalCost());
        ps.setString(8, order.getStatus() != null ? order.getStatus() : "Submitted");

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
    public ArrayList<OrderItem> getOrderById(int id) {
        ArrayList<OrderItem> listOrder = new ArrayList<>();

        String query = "SELECT P.description, OI.quantity, OI.price, OI.total_price, O.status, p.title, p.thumbnail \n"
                + "FROM \n"
                + "swp391_new.order_items OI join swp391_new.products P on P.id = OI.product_id\n"
                + "join swp391_new.orders O on O.id = OI.order_id\n"
                + "where O.id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem oi = new OrderItem();
                    Order o = new Order();
                    Products p = new Products();

                    p.setDescription(rs.getString("description"));
                    p.setTitle(rs.getString("title"));
                     p.setThumbnail(rs.getString("thumbnail"));
                    oi.setQuantity(rs.getInt("quantity"));
                    oi.setPrice(rs.getBigDecimal("price"));
                    oi.setTotalPrice(rs.getBigDecimal("total_price"));
                    o.setStatus(rs.getString("status"));
                    oi.setOrder(o);
                    oi.setProduct(p);
                    listOrder.add(oi);
                }
            }
        } catch (SQLException e) {
            System.out.println("");
        }
        return listOrder;
    }

    public List<Order> getOrdersByUserId(int userId) {
    List<Order> orders = new ArrayList<>();
    String query = "SELECT o.id AS order_id,\n"
            + "       o.user_id,\n"
            + "       o.receiver_name,\n"
            + "       o.receiver_gender,\n"
            + "       o.receiver_email,\n"
            + "       o.receiver_mobile,\n"
            + "       o.receiver_address,\n"
            + "       o.total_cost,\n"
            + "       o.status,"
            + "       o.created_at,\n"
            + "       o.updated_at \n"
            + "FROM orders o\n"
            + "JOIN users u ON o.user_id = u.id\n"
            + "WHERE o.user_id = ?\n"
            + "ORDER BY o.created_at DESC;";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, userId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order o = new Order();
                Products p = new Products();
                
                o.setId(rs.getInt("order_id"));
                o.setUserId(rs.getInt("user_id"));
                o.setReceiverName(rs.getString("receiver_name"));
                o.setReceiverGender(rs.getString("receiver_gender"));
                o.setReceiverEmail(rs.getString("receiver_email"));
                o.setReceiverMobile(rs.getString("receiver_mobile"));
                o.setReceiverAddress(rs.getString("receiver_address"));
                o.setTotalCost(rs.getBigDecimal("total_cost"));
                o.setStatus(rs.getString("status"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                o.setUpdatedAt(rs.getTimestamp("updated_at"));
                o.setProductEnt(p);
                // Assuming you want to set the product list in Order
                // You might need to handle adding this product to a list in the Order object
                

                orders.add(o);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e);
    }
    return orders;
}
    
    public Order getOrdersByUserIdByOrderId(int orderId) {
    String query = "SELECT o.id AS order_id,\n"
            + "       o.user_id,\n"
            + "       o.receiver_name,\n"
            + "       o.receiver_gender,\n"
            + "       o.receiver_email,\n"
            + "       o.receiver_mobile,\n"
            + "       o.receiver_address,\n"
            + "       o.total_cost,\n"
            + "       o.status,\n"
            + "       o.created_at,\n"
            + "       o.updated_at,\n"
            + "       p.title AS product_title,\n"
            + "       p.description AS product_description\n"
            + "FROM orders o\n"
            + "JOIN users u ON o.user_id = u.id\n"
            + "JOIN order_items oi ON o.id = oi.order_id\n"
            + "JOIN products p ON oi.product_id = p.id\n"
            + "WHERE o.id = ?\n"
            + "ORDER BY o.created_at DESC;";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, orderId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order o = new Order();
                Products p = new Products();
                
                o.setId(rs.getInt("order_id"));
                o.setUserId(rs.getInt("user_id"));
                o.setReceiverName(rs.getString("receiver_name"));
                o.setReceiverGender(rs.getString("receiver_gender"));
                o.setReceiverEmail(rs.getString("receiver_email"));
                o.setReceiverMobile(rs.getString("receiver_mobile"));
                o.setReceiverAddress(rs.getString("receiver_address"));
                o.setTotalCost(rs.getBigDecimal("total_cost"));
                o.setStatus(rs.getString("status"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                o.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                p.setTitle(rs.getString("product_title"));
                p.setDescription(rs.getString("product_description"));
                
                // Assuming you want to set the product list in Order
                // You might need to handle adding this product to a list in the Order object
                

                return o;
            }
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e);
    }
    return null;
}
}
