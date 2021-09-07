package carsharing;

import carsharing.dao.CarDao;
import carsharing.dao.Company;
import carsharing.dao.CompanyDao;

import java.util.*;
import java.util.function.Function;

public class CarSharingCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<State, Function<CarSharingCLI, Integer>> methodMap = Map.of(
            State.MAIN_MENU, CarSharingCLI::mainMenu,
            State.MANAGER_MENU, CarSharingCLI::managerMenu,
            State.GET_COMPANY_LIST, CarSharingCLI::getCompanyList,
            State.EMPTY_COMPANY_LIST, CarSharingCLI::emptyCompanyList,
            State.CHOOSE_COMPANY_MENU, CarSharingCLI::chooseCompanyMenu,
            State.COMPANY_MENU, CarSharingCLI::companyMenu,
            State.CREATE_COMPANY, CarSharingCLI::createCompany,
            State.EXIT, instance -> 0
    );

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private List<Company> companies;
    private int selectedCompanyIndex;

    public CarSharingCLI(CompanyDao companyDao, CarDao carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    public int execStateAction(State state) {
        return methodMap.get(state).apply(this);
    }

    public int mainMenu() {
        System.out.println("\n1. Log in as a manager\n0. Exit");
        return userChoice();
    }

    public int managerMenu() {
        System.out.println("\n1. Company list\n2. Create a company\n0. Back");
        return userChoice();
    }

    public int getCompanyList() {
        companies = companyDao.getAllCompanies();
        return companies.isEmpty() ? 0 : 1;
    }

    public int emptyCompanyList() {
        System.out.println("\nThe company list is empty!");
        return 0;
    }

    public int chooseCompanyMenu() {
        System.out.println("\nChoose a company:");
        companies.forEach(c -> System.out.printf("%d. %s%n", c.getId(), c.getName()));
        System.out.println("0. Back");
        selectedCompanyIndex = userChoice();
        return selectedCompanyIndex == 0 ? 0 : 1;
    }

    public int companyMenu() {
        final Company company = companies.stream().filter(c -> c.getId() == selectedCompanyIndex).findFirst().get();
        System.out.printf("'%s' company", company.getName());
        System.out.println("\nImplementing a company menu...");
        return 0;
    }

    public int createCompany() {
        System.out.println("\nEnter the company name:");
        companyDao.saveCompany(new Company(scanner.nextLine()));

        System.out.println("The company was created!");
        return 0;
    }

    private int userChoice() {
        return Integer.parseInt(scanner.nextLine());
    }

}
