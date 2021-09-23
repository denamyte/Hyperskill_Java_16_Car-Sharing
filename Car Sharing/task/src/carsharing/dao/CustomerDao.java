package carsharing.dao;

import java.util.List;

public interface CustomerDao {

    List<Customer> getAllCustomers();

    void saveCustomer(String customerName);
}
