package carsharing.dao.memory;

import carsharing.dao.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarDaoMemory extends AbstractDaoMemory<Car> implements CarDao {

    private final CustomerDao customerDao;

    public CarDaoMemory(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Car> getCarsByCompany(int companyId, boolean includeRented) {
        Stream<Car> carStream = getAll().stream().filter(car -> car.getCompanyId() == companyId);
        if (!includeRented) {
            final Set<Integer> rentedCarIds = customerDao.getAllCustomers()
                    .stream()
                    .map(Customer::getCarId)
                    .filter(id -> id > 0)
                    .collect(Collectors.toSet());
            carStream = carStream.filter(car -> !rentedCarIds.contains(car.getId()));
        }
        return carStream.collect(Collectors.toList());
    }

    @Override
    public void saveCar(Car car) {
        car.setId(nextId());
        saveItem(car);
    }

    @Override
    public Car getCarById(int carId) {
        return getById(carId);
    }

}
