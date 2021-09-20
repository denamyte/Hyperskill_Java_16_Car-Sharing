package carsharing.dao;

public class Car extends IdAndName {
    private int companyId;

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
}
