package carsharing.dao;

import java.util.List;

public interface CustomerDao {

    List<Customer> getAllCustomers();

    void saveCustomer(String customerName);

    void rentCar(int customerId, int carId);

    void returnRentedCar(int customerId);
}
