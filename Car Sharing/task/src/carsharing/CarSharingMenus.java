package carsharing;

import carsharing.dao.IdAndName;
import carsharing.state.CurrentStateDataFacade;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CarSharingMenus {

    private static final Scanner scanner = new Scanner(System.in);

    private final CurrentStateDataFacade dataFacade;

    public CarSharingMenus(CurrentStateDataFacade dataFacade) {
        this.dataFacade = dataFacade;
    }

    public int mainMenu() {
        System.out.println("\n1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit");
        return userChoice();
    }

    public int managerMenu() {
        System.out.println("\n1. Company list\n2. Create a company\n0. Back");
        return userChoice();
    }

    public int companiesList() {
        dataFacade.loadCompanies();
        if (dataFacade.noCompanies()) {
            System.out.println("\nThe company list is empty!");
            return 0;
        }
        printItemsList(dataFacade.getCompanies(), "\nChoose a company:", "0. Back");
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

    public int carListAlt() {
        dataFacade.loadSelectedCompanyCars();
        if (dataFacade.noCars()) {
            System.out.println("\nThe car list is empty!");
        } else {
            printItemsList(dataFacade.getCars(), "\nCar list:", null);
        }
        return 0;
    }

    private <T extends IdAndName> void printItemsList(List<T> items, String prefix, String suffix) {
        if (prefix != null) {
            System.out.println(prefix);
        }
        AtomicInteger counter = new AtomicInteger();
        items.forEach(item -> System.out.printf("%d. %s%n", counter.incrementAndGet(), item.getName()));
        if (suffix != null) {
            System.out.println(suffix);
        }
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
