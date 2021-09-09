package carsharing.dao.memory;

import carsharing.dao.Car;
import carsharing.dao.CarDao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarDaoMemory implements CarDao {

    private final Map<Integer, Car> carMap = new LinkedHashMap<>();

    @Override
    public List<Car> getCarsByCompanyId(int companyId) {
        return carMap.values().stream().filter(car -> car.getCompanyId() == companyId).collect(Collectors.toList());
    }
}
