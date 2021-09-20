package carsharing.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao {

    protected Connection conn;

    public BaseDao(Connection conn) {
        this.conn = conn;
        try {
            createTable(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void createTable(Connection conn) throws SQLException {
        conn.createStatement().executeUpdate(getCreateTableSQL());
    }

    protected abstract String getCreateTableSQL();
}
