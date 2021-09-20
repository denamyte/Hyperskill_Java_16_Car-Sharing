package carsharing.dao.memory;

import carsharing.dao.Company;
import carsharing.dao.CompanyDao;

import java.util.List;

public class CompanyDaoMemory extends AbstractDaoMemory<Company> implements CompanyDao {

    @Override
    public List<Company> getAllCompanies() {
        return getAll();
    }

    @Override
    public void saveCompany(String companyName) {
        saveItem(new Company(nextId(), companyName));
    }
}
