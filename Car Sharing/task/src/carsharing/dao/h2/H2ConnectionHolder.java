package carsharing.dao.h2;

import carsharing.dao.ConnectionHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionHolder implements ConnectionHolder {

    private Connection conn;

    private static Connection createConnection(String dbName) throws ClassNotFoundException, SQLException {
        Class.forName ("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:./src/carsharing/db/" + dbName);
    }

    public H2ConnectionHolder(String dbName) {
        try {
            conn = createConnection(dbName);
            conn.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return conn;
    }
}
