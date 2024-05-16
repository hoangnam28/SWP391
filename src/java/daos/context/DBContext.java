package daos.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DBContext<T> {
    protected Connection connection;

    public DBContext() throws SQLException {
        try {
            String user = "root";
            String pass = "nam281002";
            String url = "jdbc:mysql://localhost:3306/SWP";
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
    //aaa
}
