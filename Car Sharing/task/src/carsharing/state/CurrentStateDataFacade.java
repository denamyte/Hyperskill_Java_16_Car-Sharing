package carsharing.state;

import carsharing.dao.*;

import java.util.Collections;
import java.util.List;

public class CurrentStateDataFacade {
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private List<Company> companies = Collections.emptyList();
    private Company selectedCompany;
    private List<Car> cars = Collections.emptyList();

    public CurrentStateDataFacade(CompanyDao companyDao, CarDao carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    public void loadCompanies() {
        companies = companyDao.getAllCompanies();
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void saveCompany(Company company) {
        companies = Collections.emptyList();  // Invalidate companies cache
        companyDao.saveCompany(company);
    }

    public void loadSelectedCompanyCars() {
        cars = carDao.getCarsByCompanyId(selectedCompany.getId());
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setSelectedCompany(int companyId) {
        this.selectedCompany = companies.stream().filter(c -> c.getId() == companyId).findFirst().orElse(null);
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public boolean areThereCompanies() {
        return !companies.isEmpty();
    }

    public boolean areThereCars() {
        return !cars.isEmpty();
    }
}
