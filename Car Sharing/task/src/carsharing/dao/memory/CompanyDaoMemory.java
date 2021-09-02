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
    public void saveCompany(Company company) {
        company.setId(idGen.getAndIncrement());
        companyMap.put(company.getId(), company);
    }
}
