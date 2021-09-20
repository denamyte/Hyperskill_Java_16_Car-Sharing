package carsharing.dao.memory;

import carsharing.dao.Company;
import carsharing.dao.CompanyDao;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompanyDaoMemory implements CompanyDao {

    private final Map<Integer, Company> companyMap = new LinkedHashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(1);

    @Override
    public List<Company> getAllCompanies() {
        return new ArrayList<>(companyMap.values());
    }

    @Override
    public void saveCompany(String companyName) {
        Company company = new Company(idGen.getAndIncrement(), companyName);
        companyMap.put(company.getId(), company);
    }
}
