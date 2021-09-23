package carsharing.state;

import carsharing.dao.*;

import java.util.Collections;
import java.util.List;

public class CurrentStateDataFacade {
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;
    private List<Company> companies = Collections.emptyList();
    private Company selectedCompany;
    private boolean selectedCompanyPrinted;
    private List<Car> cars = Collections.emptyList();
    private List<Customer> customers = Collections.emptyList();
    private Customer selectedCustomer;

    public CurrentStateDataFacade(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }

    public void loadCompanies() {
        companies = companyDao.getAllCompanies();
    }

    public List<Company> getCompanies() {
        if (companies.isEmpty()) {
            loadCompanies();
        }
        return companies;
    }

    public void saveCompany(String companyName) {
        companies = Collections.emptyList();  // Invalidate companies cache
        companyDao.saveCompany(companyName);
    }

    public void setSelectedCompany(int choice) {
        selectedCompany = companies.stream()
                .skip(choice - 1)
                .findFirst().orElseThrow();
        selectedCompanyPrinted = false;
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public boolean canPrintSelectedCompany() {
        boolean result = !selectedCompanyPrinted;
        selectedCompanyPrinted = true;
        return result;
    }

    public void loadSelectedCompanyCars() {
        cars = carDao.getCarsByCompanyId(selectedCompany.getId());
    }

    public List<Car> getCars() {
        if (cars.isEmpty()) {
            loadSelectedCompanyCars();
        }
        return cars;
    }

    public void saveCar(String carName) {
        cars = Collections.emptyList();  // Invalidate cars cache
        carDao.saveCar(new Car(0, carName, selectedCompany.getId()));
    }

    public void loadCustomers() {
        customers = customerDao.getAllCustomers();
    }

    public List<Customer> getCustomers() {
        if (customers.isEmpty()) {
            loadCustomers();
        }
        return customers;
    }

    public void saveCustomer(String customerName) {
        customers = Collections.emptyList();  // Invalidate customers cache
        customerDao.saveCustomer(customerName);
    }

    public void setSelectedCustomer(int choice) {
        selectedCustomer = customers.stream()
                .skip(choice - 1)
                .findFirst().orElseThrow();
    }

    public boolean noCompanies() {
        return companies.isEmpty();
    }

    public boolean noCars() {
        return cars.isEmpty();
    }

    public boolean noCustomers() {
        return customers.isEmpty();
    }
}
