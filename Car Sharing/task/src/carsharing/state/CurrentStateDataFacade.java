package carsharing.state;

import carsharing.dao.*;

import java.util.Collections;
import java.util.List;

public class CurrentStateDataFacade {
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private List<Company> companies = Collections.emptyList();
    private Company selectedCompany;
    private boolean selectedCompanyPrinted;
    private List<Car> cars = Collections.emptyList();

    public CurrentStateDataFacade(CompanyDao companyDao, CarDao carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
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
        companyDao.saveCompany(new Company(companyName));
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
        carDao.saveCar(new Car(carName, selectedCompany.getId()));
    }

    public void setSelectedCompany(int companyId) {
        selectedCompany = companies.stream()
                .skip(companyId - 1)
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

    public boolean areThereCompanies() {
        return !companies.isEmpty();
    }

    public boolean areThereCars() {
        return !cars.isEmpty();
    }
}
