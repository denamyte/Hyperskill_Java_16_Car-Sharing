package carsharing.dao;

import java.util.List;

public interface CarDao {

    List<Car> getCarsByCompany(int companyId, boolean includeRented);

    void saveCar(Car car);

    Car getCarById(int carId);
}
