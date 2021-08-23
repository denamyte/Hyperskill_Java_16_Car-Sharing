package carsharing;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CompanyDaoH2 implements CompanyDao {

    private static final String TABLE_NAME = "COMPANY";
    private Connection conn;

    private static Connection createConnection(String dbName) throws ClassNotFoundException, SQLException {
        Class.forName ("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:./src/carsharing/db/" + dbName);
    }

    private static void createTable(Connection conn) {
        try {
            final Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                                       "ID INT PRIMARY KEY AUTO_INCREMENT," +
                                       "NAME VARCHAR NOT NULL UNIQUE);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompanyDaoH2(String dbName) {
        try {
            conn = createConnection(dbName);
            conn.setAutoCommit(true);
            createTable(conn);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> list = new LinkedList<>();

        // TODO: Query the DB and map results to the list

        return list;
    }

    @Override
    public void saveCompany(Company company) {

        // TODO: insert a new record
    }

}
