package carsharing.dao.h2;

import carsharing.dao.BaseDao;
import carsharing.dao.Car;
import carsharing.dao.CarDao;

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
    public static final String SAVE_CAR_SQL =
            "INSERT INTO CAR(NAME, COMPANY_ID) VALUES(?, ?)";
    public static final String COMPANY_CARS_SQL =
            "SELECT ID, NAME FROM CAR " +
                    "WHERE COMPANY_ID = ?";

    public CarDaoH2(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    public List<Car> getCarsByCompanyId(int companyId) {
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
}
