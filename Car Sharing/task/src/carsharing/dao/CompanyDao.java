package carsharing.dao;

import java.util.List;

public interface CompanyDao {

    List<Company> getAllCompanies();

    void saveCompany(Company company);
}
