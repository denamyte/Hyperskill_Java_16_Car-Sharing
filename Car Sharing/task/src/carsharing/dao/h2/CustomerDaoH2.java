package carsharing.dao.h2;

import carsharing.dao.BaseDao;
import carsharing.dao.Customer;
import carsharing.dao.CustomerDao;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDaoH2 extends BaseDao implements CustomerDao {

    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR NOT NULL UNIQUE," +
                    "RENTED_CAR_ID INT DEFAULT NULL," +
                    "CONSTRAINT fk_CAR FOREIGN KEY(RENTED_CAR_ID)" +
                    "REFERENCES CAR(ID));";
    public static final String SELECT_CUSTOMERS_SQL =
            "SELECT ID, NAME, RENTED_CAR_ID FROM CUSTOMER;";
    public static final String SAVE_CUSTOMER_SQL =
            "INSERT INTO CUSTOMER(NAME) VALUES(?)";
    public static final String RENT_CAR_SQL =
            "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
    public static final String RETURN_RENTED_CAR_SQL =
            "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";

    public CustomerDaoH2(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new LinkedList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_CUSTOMERS_SQL)) {
            final ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt("ID"), resultSet.getString("NAME"))
                                      .setCarId(resultSet.getInt("RENTED_CAR_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void saveCustomer(String customerName) {
        try (PreparedStatement stmt = conn.prepareStatement(SAVE_CUSTOMER_SQL)) {
            stmt.setString(1, customerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rentCar(int customerId, int carId) {
        try (PreparedStatement stmt = conn.prepareStatement(RENT_CAR_SQL)){
            stmt.setInt(1, carId);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnRentedCar(int customerId) {
        try (PreparedStatement stmt = conn.prepareStatement(RETURN_RENTED_CAR_SQL)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
