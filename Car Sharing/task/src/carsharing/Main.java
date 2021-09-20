package carsharing;

import carsharing.dao.*;
import carsharing.dao.h2.*;
import carsharing.dao.memory.CarDaoMemory;
import carsharing.dao.memory.CompanyDaoMemory;
import carsharing.dao.memory.CustomerDaoMemory;
import carsharing.state.CurrentStateDataFacade;
import carsharing.state.StateMachineFactory;

public class Main {

    private static final String DEFAULT_DB_NAME = "carsharing";

    public static void main(String[] args) {
        CarSharingMenus menus = new CarSharingMenus(getH2DataFacade(args));
        new StateMachineFactory(menus).createStateMachineController().run();
    }

    private static CurrentStateDataFacade getMemoryDataFacade() {
        final CompanyDaoMemory companyDao = new CompanyDaoMemory();
        final CarDaoMemory carDao = new CarDaoMemory();
        final CustomerDao customerDao = new CustomerDaoMemory();
        return new CurrentStateDataFacade(companyDao, carDao, customerDao);
    }

    private static CurrentStateDataFacade getH2DataFacade(String[] args) {
        ConnectionHolder holder = new H2ConnectionHolder(getDBName(args));
        CompanyDao companyDao = new CompanyDaoH2(holder.getConnection());
        CarDao carDao = new CarDaoH2(holder.getConnection());
        CustomerDao customerDao = new CustomerDaoH2(holder.getConnection());
        return new CurrentStateDataFacade(companyDao, carDao, customerDao);
    }

    private static String getDBName(String[] args) {
        return args.length >= 2 && args[0].equals("-databaseFileName")
                ? args[1]
                : DEFAULT_DB_NAME;
    }
}