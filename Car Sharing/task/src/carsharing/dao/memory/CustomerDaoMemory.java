package carsharing.dao.memory;

import carsharing.dao.Customer;
import carsharing.dao.CustomerDao;

import java.util.List;

public class CustomerDaoMemory extends AbstractDaoMemory<Customer> implements CustomerDao {
    @Override
    public List<Customer> getAllCustomers() {
        return getAll();
    }

    @Override
    public void saveCustomer(String customerName) {
        saveItem(new Customer(nextId(), customerName));
    }

    @Override
    public void rentCar(int customerId, int carId) {
        final Customer customer = getById(customerId);
        if (customer != null) {
            customer.setCarId(carId);
        }
    }

    @Override
    public void returnRentedCar(int customerId) {
        getById(customerId).setCarId(0);
    }
}
