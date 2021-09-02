package carsharing;

import carsharing.dao.ConnectionHolder;
import carsharing.dao.h2.H2ConnectionHolder;
import carsharing.dao.CompanyDao;
import carsharing.dao.h2.CompanyDaoH2;

public class Main {

    private static final String DEFAULT_DB_NAME = "carsharing";

    public static void main(String[] args) {

        ConnectionHolder holder = new H2ConnectionHolder(getDBName(args));
        CompanyDao companyDao = new CompanyDaoH2(holder.getConnection());
        CarSharingCLI cli = new CarSharingCLI(companyDao);
        StateMachine stateMachine = new StateMachine();

        Controller controller = new Controller(cli, stateMachine);
        controller.programLoop();
    }

    private static String getDBName(String[] args) {
        return args.length >= 2 && args[0].equals("-databaseFileName")
                ? args[1]
                : DEFAULT_DB_NAME;
    }

}