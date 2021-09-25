package carsharing.dao;

import java.util.List;

public interface CarDao {

    List<Car> getCarsByCompanyId(int companyId);

    void saveCar(Car car);

    Car getCarById(int carId);
}
