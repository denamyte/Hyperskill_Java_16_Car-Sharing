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
}
