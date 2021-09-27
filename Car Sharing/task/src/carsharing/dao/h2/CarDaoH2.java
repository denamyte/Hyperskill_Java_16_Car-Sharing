package carsharing.dao.h2;

import carsharing.dao.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CarDaoH2 extends BaseDao implements CarDao {

    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS CAR(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR NOT NULL UNIQUE," +
                    "COMPANY_ID INT NOT NULL," +
                    "CONSTRAINT fk_COMPANY FOREIGN KEY(COMPANY_ID)" +
                    "REFERENCES COMPANY(ID));";
    public static final String COMPANY_CARS_SQL =
            "SELECT ID, NAME FROM CAR " +
                    "WHERE COMPANY_ID = ?";
    public static final String SAVE_CAR_SQL =
            "INSERT INTO CAR(NAME, COMPANY_ID) VALUES(?, ?)";
    public static final String GET_CAR_BY_ID_SQL =
            "SELECT c.NAME CAR_NAME, co.ID CO_ID, co.NAME CO_NAME FROM CAR c " +
                    "JOIN COMPANY co " +
                    "ON c.COMPANY_ID = co.ID " +
                    "WHERE c.ID = ?";

    public CarDaoH2(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    public List<Car> getCarsByCompanyId(int companyId) {

        // TODO: 9/27/21 The cars returned shouldn't be rented by a customer (some JOIN with CUSTOMER table needed)

        List<Car> cars = new LinkedList<>();
        try (PreparedStatement stmt = conn.prepareStatement(COMPANY_CARS_SQL)) {
            stmt.setInt(1, companyId);
            final ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                cars.add(new Car(resultSet.getInt("ID"), resultSet.getString("NAME"), companyId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void saveCar(Car car) {
        try (PreparedStatement stmt = conn.prepareStatement(SAVE_CAR_SQL)){
            stmt.setString(1, car.getName());
            stmt.setInt(2, car.getCompanyId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car getCarById(int carId) {
        try (PreparedStatement stmt = conn.prepareStatement(GET_CAR_BY_ID_SQL)) {
            stmt.setInt(1, carId);
            final ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                final int companyId = resultSet.getInt("CO_ID");
                return new Car(carId, resultSet.getString("CAR_NAME"), companyId)
                        .setCompany(new Company(companyId, resultSet.getString("CO_NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
