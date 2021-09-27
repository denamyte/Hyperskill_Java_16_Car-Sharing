package carsharing.dao;

public class Car extends BaseItem {
    private int companyId;
    private Company company;

    public Car(int id, String name, int companyId) {
        super(id, name);
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Car setCompany(Company company) {
        this.company = company;
        return this;
    }

    public String getCompanyName() {
        return company == null ? "" : company.getName();
    }
}
