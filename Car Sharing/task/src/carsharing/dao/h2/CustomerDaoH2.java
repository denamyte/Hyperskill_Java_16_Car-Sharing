package carsharing.dao.h2;

import carsharing.dao.BaseDao;
import carsharing.dao.Customer;
import carsharing.dao.CustomerDao;

import java.sql.Connection;
import java.util.List;

public class CustomerDaoH2 extends BaseDao implements CustomerDao {

    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR NOT NULL UNIQUE," +
                    "RENTED_CAR_ID INT," +
                    "CONSTRAINT fk_CAR FOREIGN KEY(RENTED_CAR_ID)" +
                    "REFERENCES CAR(ID));";

    public CustomerDaoH2(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }
}
