package carsharing;

import carsharing.state.CurrentStateDataFacade;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CarSharingMenus {

    private static final Scanner scanner = new Scanner(System.in);

    private CurrentStateDataFacade dataFacade;

    public CarSharingMenus(CurrentStateDataFacade dataFacade) {
        this.dataFacade = dataFacade;
    }

    public int mainMenu() {
        System.out.println("\n1. Log in as a manager\n0. Exit");
        return userChoice();
    }

    public int managerMenu() {
        System.out.println("\n1. Company list\n2. Create a company\n0. Back");
        return userChoice();
    }

    public int loadCompanyList() {
        dataFacade.loadCompanies();
        return dataFacade.areThereCompanies() ? 1 : 0;
    }

    public int emptyCompanyList() {
        System.out.println("\nThe company list is empty!");
        return 0;
    }

    public int chooseCompanyMenu() {
        System.out.println("\nChoose a company:");
        AtomicInteger counter = new AtomicInteger();
        dataFacade.getCompanies().forEach(c -> System.out.printf("%d. %s%n", counter.incrementAndGet(), c.getName()));
        System.out.println("0. Back");
        int choice = userChoice();
        if (choice == 0) {
            return 0;
        } else {
            dataFacade.setSelectedCompany(choice);
            return 1;
        }
    }

    public int companyMenu() {
        if (dataFacade.canPrintSelectedCompany()) {
            System.out.printf("\n'%s' company", dataFacade.getSelectedCompany().getName());
        }
        System.out.println("\n1. Car list\n2. Create a car\n0. Back");
        return userChoice();
    }

    public int loadCarList() {
        dataFacade.loadSelectedCompanyCars();
        return dataFacade.areThereCars() ? 1 : 0;
    }

    public int emptyCarList() {
        System.out.println("\nThe car list is empty!");
        return 0;
    }

    public int carList() {
        System.out.println("\nCar list:");
        AtomicInteger counter = new AtomicInteger();
        dataFacade.getCars().forEach(car -> System.out.printf("%d. %s%n", counter.incrementAndGet(), car.getName()));
        return 0;
    }

    public int createCar() {
        System.out.println("\nEnter the car name:");
        dataFacade.saveCar(scanner.nextLine());
        System.out.println("The car was added!");
        return 0;
    }


    public int createCompany() {
        System.out.println("\nEnter the company name:");
        dataFacade.saveCompany(scanner.nextLine());
        System.out.println("The company was created!");
        return 0;
    }

    private int userChoice() {
        return Integer.parseInt(scanner.nextLine());
    }
}
