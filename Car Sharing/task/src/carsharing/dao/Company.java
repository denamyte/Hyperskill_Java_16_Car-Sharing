package carsharing.dao;

public class Company extends BaseItem {
    public Company(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
