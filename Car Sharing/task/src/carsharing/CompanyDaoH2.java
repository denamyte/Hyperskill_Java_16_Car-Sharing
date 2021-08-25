package carsharing;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CompanyDaoH2 implements CompanyDao {

    private PreparedStatement getCompaniesStmt;
    private PreparedStatement insertCompanyStmt;
    private final Object insertLock = new Object();
    private Connection conn;

    private static Connection createConnection(String dbName) throws ClassNotFoundException, SQLException {
        Class.forName ("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:./src/carsharing/db/" + dbName);
    }

    private static void createTable(Connection conn) {
        try {
            conn.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY(" +
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
            prepareStatements(conn);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void prepareStatements(Connection conn) throws SQLException {
        getCompaniesStmt = conn.prepareStatement("SELECT ID, NAME FROM COMPANY;");
        insertCompanyStmt = conn.prepareStatement("INSERT INTO COMPANY(NAME) VALUES(?)");
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> list = new LinkedList<>();
        try {
            final ResultSet resultSet = getCompaniesStmt.executeQuery();
            while (resultSet.next()) {
                list.add(new Company(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void saveCompany(Company company) {
        synchronized (insertLock) {
            try {
                insertCompanyStmt.setString(1, company.getName());
                insertCompanyStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
