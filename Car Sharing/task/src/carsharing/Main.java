package carsharing;

import carsharing.dao.CarDao;
import carsharing.dao.ConnectionHolder;
import carsharing.dao.h2.CarDaoH2;
import carsharing.dao.h2.H2ConnectionHolder;
import carsharing.dao.CompanyDao;
import carsharing.dao.h2.CompanyDaoH2;
import carsharing.dao.memory.CarDaoMemory;
import carsharing.dao.memory.CompanyDaoMemory;

public class Main {

    private static final String DEFAULT_DB_NAME = "carsharing";

    public static void main(String[] args) {

//        CarSharingCLI cli = getMemoryImpl();
        CarSharingCLI cli = getH2Impl(args);

        StateMachine stateMachine = new StateMachine();
        Controller controller = new Controller(cli, stateMachine);
        controller.programLoop();
    }

    private static String getDBName(String[] args) {
        return args.length >= 2 && args[0].equals("-databaseFileName")
                ? args[1]
                : DEFAULT_DB_NAME;
    }

    private static CarSharingCLI getMemoryImpl() {
        return new CarSharingCLI(new CompanyDaoMemory(), new CarDaoMemory());
    }

    private static CarSharingCLI getH2Impl(String[] args) {
        ConnectionHolder holder = new H2ConnectionHolder(getDBName(args));
        CompanyDao companyDao = new CompanyDaoH2(holder.getConnection());
        CarDao carDao = new CarDaoH2(holder.getConnection());
        return new CarSharingCLI(companyDao, carDao);
    }
}