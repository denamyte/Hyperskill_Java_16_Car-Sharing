package carsharing.dao.memory;

import carsharing.dao.Car;
import carsharing.dao.CarDao;

import java.util.List;
import java.util.stream.Collectors;

public class CarDaoMemory extends AbstractDaoMemory<Car> implements CarDao {

    @Override
    public List<Car> getCarsByCompanyId(int companyId) {
        return getAll().stream().filter(car -> car.getCompanyId() == companyId).collect(Collectors.toList());
    }

    @Override
    public void saveCar(Car car) {
        car.setId(nextId());
        saveItem(car);
    }

    @Override
    public Car getCarById(int carId) {
        return itemMap.get(carId);
    }

}
