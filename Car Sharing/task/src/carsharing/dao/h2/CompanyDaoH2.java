package carsharing.dao.h2;

import carsharing.dao.BaseDao;
import carsharing.dao.Company;
import carsharing.dao.CompanyDao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CompanyDaoH2 extends BaseDao implements CompanyDao {

    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS COMPANY(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR NOT NULL UNIQUE);";
    public static final String SELECT_COMPANIES_SQL =
            "SELECT ID, NAME FROM COMPANY;";

    private final Object insertLock = new Object();

    public CompanyDaoH2(Connection conn) {
        super(conn);
    }

    @Override
    protected void createTable(Connection conn) throws SQLException {
        conn.createStatement().executeUpdate(CREATE_TABLE_SQL);
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new LinkedList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_COMPANIES_SQL)) {
            final ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void saveCompany(Company company) {
        synchronized (insertLock) {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO COMPANY(NAME) VALUES(?)")) {
                stmt.setString(1, company.getName());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
