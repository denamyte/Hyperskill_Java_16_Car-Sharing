package carsharing.dao;

import java.util.List;

public interface CarDao {

    List<Car> getCarsByCompanyId(int companyId);
}
