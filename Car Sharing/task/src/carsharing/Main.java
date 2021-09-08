package carsharing;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.ConnectionHolder;
import carsharing.dao.h2.CarDaoH2;
import carsharing.dao.h2.CompanyDaoH2;
import carsharing.dao.h2.H2ConnectionHolder;
import carsharing.dao.memory.CarDaoMemory;
import carsharing.dao.memory.CompanyDaoMemory;
import carsharing.state.StateMachineFactory;

public class Main {

    private static final String DEFAULT_DB_NAME = "carsharing";

    public static void main(String[] args) {
        CarSharingCLI cli = getH2CliImpl(args);
        new StateMachineFactory(cli).createStateMachineController().run();
    }

    private static CarSharingCLI getMemoryCliImpl() {
        return new CarSharingCLI(new CompanyDaoMemory(), new CarDaoMemory());
    }

    private static CarSharingCLI getH2CliImpl(String[] args) {
        ConnectionHolder holder = new H2ConnectionHolder(getDBName(args));
        CompanyDao companyDao = new CompanyDaoH2(holder.getConnection());
        CarDao carDao = new CarDaoH2(holder.getConnection());
        return new CarSharingCLI(companyDao, carDao);
    }

    private static String getDBName(String[] args) {
        return args.length >= 2 && args[0].equals("-databaseFileName")
                ? args[1]
                : DEFAULT_DB_NAME;
    }
}