package carsharing.dao;

import java.sql.Connection;

public interface ConnectionHolder {
    Connection getConnection();
}
