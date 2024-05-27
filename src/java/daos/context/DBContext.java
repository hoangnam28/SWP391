package daos.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DBContext<T> {
    protected Connection connection;

    public DBContext() throws SQLException {
        try {
            String user = "root";
            String pass = "123456";
            String url = "jdbc:mysql://localhost:3306/swp301";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Cannot connect to database", ex);
        }
    }

    public abstract List<T> findAll();
    public abstract T findById(int id);
    public abstract T update(T model);
    public abstract void deleteById(int id);

    // Concrete subclass for testing
    public static class TestDBContext extends DBContext<Object> {
        public TestDBContext() throws SQLException {
            super();
        }

        @Override
        public List<Object> findAll() {
            // Dummy implementation
            return null;
        }

        @Override
        public Object findById(int id) {
            // Dummy implementation
            return null;
        }

        @Override
        public Object update(Object model) {
            // Dummy implementation
            return null;
        }

        @Override
        public void deleteById(int id) {
            // Dummy implementation
        }

//        public static void main(String[] args) {
//            try {
//                TestDBContext dbContext = new TestDBContext();
//                if (dbContext.connection != null && !dbContext.connection.isClosed()) {
//                    System.out.println("Connection to the database was successful!");
//                } else {
//                    System.out.println("Failed to connect to the database.");
//                }
//            } catch (SQLException e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
    }
}
