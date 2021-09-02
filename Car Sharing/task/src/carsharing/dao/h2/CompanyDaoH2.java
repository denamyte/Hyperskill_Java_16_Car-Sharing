package carsharing.dao.h2;

import carsharing.dao.JDBCDao;
import carsharing.dao.Company;
import carsharing.dao.CompanyDao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CompanyDaoH2 extends JDBCDao implements CompanyDao {

    private PreparedStatement getCompaniesStmt;
    private PreparedStatement insertCompanyStmt;
    private final Object insertLock = new Object();


    public CompanyDaoH2(Connection conn) {
        super(conn);
    }

    @Override
    protected void createTable(Connection conn) throws SQLException {
        conn.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY(" +
                                                     "ID INT PRIMARY KEY AUTO_INCREMENT," +
                                                     "NAME VARCHAR NOT NULL UNIQUE);"
        );
    }

    @Override
    protected void prepareStatements(Connection conn) throws SQLException {
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
