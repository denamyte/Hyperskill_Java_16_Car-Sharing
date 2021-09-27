package carsharing.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDao {

    protected Connection conn;

    public BaseDao(Connection conn) {
        this.conn = conn;
        createTable();
    }

    private void createTable() {
        try (Statement stmt = conn.createStatement()){
            stmt.executeUpdate(getCreateTableSQL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getCreateTableSQL();
}
