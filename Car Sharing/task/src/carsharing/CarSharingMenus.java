package carsharing;

import carsharing.dao.*;
import carsharing.state.CurrentStateDataFacade;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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

    public int companiesListChoice() {
        final List<Company> companies = dataFacade.getCompanies();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            return 0;
        }
        printItemsList(companies, "\nChoose a company:", "0. Back");
        int choice = userChoice();
        if (choice == 0) {
            return 0;
        } else {
            dataFacade.setSelectedCompany(choice);
            dataFacade.flushCars();
            return 1;
        }
    }

    public int companyMenu() {
        if (dataFacade.canPrintSelectedCompany()) {
            System.out.printf("\n'%s' company", dataFacade.getSelectedCompanyName());
        }
        System.out.println("\n1. Car list\n2. Create a car\n0. Back");
        return userChoice();
    }

    public int viewCarListAsCompany() {
        carList("\nThe car list is empty!", "\nCar list:", null);
        return 0;
    }

    public void viewCarListAsCustomer(String companyName) {
        carList(String.format("\nNo available cars in the %s company\n", companyName),
                "\nChoose a car:", "0. Back");
    }

    private void carList(String emptyListMessage, String listPrefix, String listSuffix) {
        final List<Car> cars = dataFacade.getCars();
        if (cars.isEmpty()) {
            System.out.println(emptyListMessage);
        } else {
            printItemsList(cars, listPrefix, listSuffix);
        }
    }

    public int customerListChoice() {
        final List<Customer> customers = dataFacade.getCustomers();
        if (customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!");
            return 0;
        }
        printItemsList(customers, "\nCustomer list:", "0. Back");
        int choice = userChoice();
        if (choice == 0) {
            return 0;
        } else {
            dataFacade.setSelectedCustomer(choice);
            return 1;
        }
    }

    public int customerMenu() {
        System.out.println("\n1. Rent a car\n2. Return a rented car\n3. My rented car\n0. Back");
        return userChoice();
    }

    public int rentCar() {
        if (dataFacade.carIsRentedBySelectedCustomer()) {
            System.out.println("\nYou've already rented a car!");
            return 0;
        } else {
            final int companiesChoice = companiesListChoice();
            if (companiesChoice != 0) {
                viewCarListAsCustomer(dataFacade.getSelectedCompanyName());
                if (dataFacade.getCars().isEmpty()) {
                    return 0;
                }
                final int carsChoice = userChoice();
                if (carsChoice > 0) {
                    dataFacade.setSelectedCar(carsChoice);
                    dataFacade.rentCar();
                    System.out.printf("\nYou rented '%s'\n", dataFacade.getSelectedCarName());
                }
            }
        }
        return 0;
    }

    public int returnRentedCar() {
        if (dataFacade.carIsRentedBySelectedCustomer()) {
            dataFacade.returnRentedCar();
            System.out.println("\nYou've returned a rented car!");
        } else {
            didNotRentMessage();
        }
        return 0;
    }

    public int viewRentedCar() {
        if (dataFacade.carIsRentedBySelectedCustomer()) {
            Car car = dataFacade.getRentedCarOfSelectedCustomer();
            System.out.printf("\nYour rented car:\n%s\nCompany:\n%s\n", car.getName(), car.getCompanyName());
        } else {
            didNotRentMessage();
        }
        return 0;
    }

    private void didNotRentMessage() {
        System.out.println("\nYou didn't rent a car!");
    }

    public int createCompany() {
        dataFacade.flushCompanies();
        return saveTemplate("\nEnter the company name:", dataFacade::saveCompany, "The company was created!");
    }

    public int createCar() {
        dataFacade.flushCars();
        return saveTemplate("\nEnter the car name:", dataFacade::saveCar, "The car was added!");
    }

    public int createCustomer() {
        dataFacade.flushCustomers();
        return saveTemplate("\nEnter the customer name:", dataFacade::saveCustomer, "The customer was added!");
    }

    private int saveTemplate(String enterPrompt, Consumer<String> saveFn, String message) {
        System.out.println(enterPrompt);
        saveFn.accept(scanner.nextLine());
        System.out.println(message);
        return 0;
    }

    private <T extends BaseItem> void printItemsList(List<T> items, String prefix, String suffix) {
        if (prefix != null) {
            System.out.println(prefix);
        }
        AtomicInteger counter = new AtomicInteger();
        items.forEach(item -> System.out.printf("%d. %s%n", counter.incrementAndGet(), item.getName()));
        if (suffix != null) {
            System.out.println(suffix);
        }
    }

    private int userChoice() {
        return Integer.parseInt(scanner.nextLine());
    }
}
