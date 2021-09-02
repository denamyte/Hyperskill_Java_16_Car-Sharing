package carsharing.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JDBCDao {

    public JDBCDao(Connection conn) {
        try {
            createTable(conn);
            prepareStatements(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract void createTable(Connection conn) throws SQLException;

    protected abstract void prepareStatements(Connection conn) throws SQLException;
}
