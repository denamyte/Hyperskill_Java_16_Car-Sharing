package carsharing.dao;

public class Customer extends BaseItem {
    private int carId;

    public Customer(int id, String name) {
        super(id, name);
    }

    public Customer setCarId(int carId) {
        this.carId = carId;
        return this;
    }

    public int getCarId() {
        return carId;
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
