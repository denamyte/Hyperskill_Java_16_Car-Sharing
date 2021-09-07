package carsharing.dao.h2;

import carsharing.dao.JDBCDao;
import carsharing.dao.CarDao;

import java.sql.Connection;
import java.sql.SQLException;

public class CarDaoH2 extends JDBCDao implements CarDao {

    public CarDaoH2(Connection conn) {
        super(conn);
    }

    @Override
    protected void createTable(Connection conn) throws SQLException {
        conn.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS CAR(" +
                                                     "ID INT PRIMARY KEY AUTO_INCREMENT," +
                                                     "NAME VARCHAR NOT NULL UNIQUE," +
                                                     "COMPANY_ID INT NOT NULL," +
                                                     "CONSTRAINT fk_COMPANY FOREIGN KEY(COMPANY_ID)" +
                                                     "REFERENCES COMPANY(ID));"
        );
    }

}
