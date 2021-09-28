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
    private List<Customer> customers = Collections.emptyList();
    private Customer selectedCustomer;

    public CurrentStateDataFacade(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }

    public List<Company> getCompanies() {
        if (companies.isEmpty()) {
            companies = companyDao.getAllCompanies();
        }
        return companies;
    }

    public void saveCompany(String companyName) {
        companyDao.saveCompany(companyName);
    }

    public void flushCompanies() {
        companies = Collections.emptyList();
    }

    public void setSelectedCompany(int choice) {
        selectedCompany = selectItemByIndex(companies, choice);
        selectedCompanyPrinted = false;
    }

    public String getSelectedCompanyName() {
        return selectedCompany == null ? "" : selectedCompany.getName();
    }

    public boolean canPrintSelectedCompany() {
        boolean result = !selectedCompanyPrinted;
        selectedCompanyPrinted = true;
        return result;
    }

    public List<Car> getCars(boolean includeRented) {
        return carDao.getCarsByCompany(selectedCompany.getId(), includeRented);
    }

    public void saveCar(String carName) {
        carDao.saveCar(new Car(0, carName, selectedCompany.getId()));
    }

    public List<Customer> getCustomers() {
        if (customers.isEmpty()) {
            customers = customerDao.getAllCustomers();
        }
        return customers;
    }

    public void saveCustomer(String customerName) {
        customers = Collections.emptyList();  // Invalidate customers cache
        customerDao.saveCustomer(customerName);
    }

    public void flushCustomers() {
        customers = Collections.emptyList();
    }

    public void setSelectedCustomer(int choice) {
        selectedCustomer = selectItemByIndex(customers, choice);
    }

    public Car getRentedCarOfSelectedCustomer() {
        return carDao.getCarById(selectedCustomer.getCarId());
    }

    public boolean carIsRentedBySelectedCustomer() {
        return selectedCustomer != null && selectedCustomer.getCarId() > 0;
    }

    public void returnRentedCar() {
        customerDao.returnRentedCar(selectedCustomer.getId());
        selectedCustomer.setCarId(0);
    }

    public Car getSelectedCar(List<Car> cars, int choice) {
        return selectItemByIndex(cars, choice);
    }

    public void rentCar(int carId) {
        customerDao.rentCar(selectedCustomer.getId(), carId);
        selectedCustomer.setCarId(carId);
    }

    private <T> T selectItemByIndex(List<T> items, int choice) {
        return items.stream()
                .skip(choice - 1)
                .findFirst().orElseThrow();
    }
}
