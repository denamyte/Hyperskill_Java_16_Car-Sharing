package carsharing;

public class Main {

    public static void main(String[] args) {

        CompanyDao companyDao = new CompanyDaoMemory();
        CarSharingCLI cli = new CarSharingCLI(companyDao);
        StateMachine stateMachine = new StateMachine();

        Controller controller = new Controller(cli, stateMachine);
        controller.programLoop();
    }
}