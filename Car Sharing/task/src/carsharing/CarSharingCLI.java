package carsharing;

import carsharing.dao.Company;
import carsharing.dao.CompanyDao;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class CarSharingCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<State, Function<CarSharingCLI, Integer>> methodMap = Map.of(
            State.MAIN_MENU, CarSharingCLI::mainMenu,
            State.LOGGED_AS_MANAGER, CarSharingCLI::managerMenu,
            State.COMPANY_LIST, CarSharingCLI::companyList,
            State.CREATE_COMPANY, CarSharingCLI::createCompany,
            State.EXIT, instance -> 0
    );

    private final CompanyDao companyDao;

    public CarSharingCLI(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public int execStateAction(State state) {
        return methodMap.get(state).apply(this);
    }

    private int mainMenu() {
        System.out.println("\n1. Log in as a manager\n0. Exit");
        return Integer.parseInt(scanner.nextLine());
    }

    private int managerMenu() {
        System.out.println("\n1. Company list\n2. Create a company\n0. Back");
        return Integer.parseInt(scanner.nextLine());
    }

    private int companyList() {
        final List<Company> companies = companyDao.getAllCompanies();

        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nCompany list:");
            companies.forEach(c -> System.out.printf("%d. %s%n", c.getId(), c.getName()));
        }

        return 0;
    }

    private int createCompany() {
        System.out.println("\nEnter the company name:");
        companyDao.saveCompany(new Company(scanner.nextLine()));

        System.out.println("The company was created!");
        return 0;
    }

}
