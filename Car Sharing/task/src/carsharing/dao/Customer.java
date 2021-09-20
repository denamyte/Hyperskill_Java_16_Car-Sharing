package carsharing.dao;

public class Customer extends IdAndName {
    private int carId;

    public Customer(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "carId=" + carId +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
