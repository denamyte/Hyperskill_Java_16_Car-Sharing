package carsharing.state;

import carsharing.CarSharingMenus;
import carsharing.state.model.StateMachineController;
import carsharing.state.model.StateTransition;

import java.util.*;
import java.util.stream.Collectors;

public class StateMachineFactory {

    private final CarSharingMenus menus;

    enum State {
        MAIN_MENU,
        LOGIN_AS_MANAGER,
        LOGIN_AS_CUSTOMER,
        CREATE_CUSTOMER,

        COMPANIES_LIST,
        COMPANY_MENU,
        CREATE_COMPANY,

        CAR_LIST,
        CREATE_CAR,

        CUSTOMER_MENU,  // NEW
        RENT_CAR,  // NEW
        RETURN_RENTED_CAR,  // NEW
        RENTED_CAR_VIEW,  // NEW

        EXIT
    }

    public StateMachineFactory(CarSharingMenus menus) {
        this.menus = menus;
    }

    public StateMachineController createStateMachineController() {
        return new StateMachineController(createTransitionMap(), State.MAIN_MENU.name());
    }

    private Map<String, StateTransition> createTransitionMap() {
        List<StateTransition> transList = List.of(
                new StateTransition(State.MAIN_MENU.name(),
                                    Map.of(1, State.LOGIN_AS_MANAGER.name(),
                                           2, State.LOGIN_AS_CUSTOMER.name(),
                                           3, State.CREATE_CUSTOMER.name(),
                                           0, State.EXIT.name()),
                                    menus::mainMenu
                ),

                new StateTransition(State.LOGIN_AS_MANAGER.name(),
                                    Map.of(1, State.COMPANIES_LIST.name(),
                                           2, State.CREATE_COMPANY.name(),
                                           0, State.MAIN_MENU.name()),
                                    menus::managerMenu
                ),
                new StateTransition(State.LOGIN_AS_CUSTOMER.name(),
                                    Map.of(0, State.MAIN_MENU.name(),
                                           1, State.CUSTOMER_MENU.name()),
                                    menus::customerListChoice
                ),
                new StateTransition(State.CREATE_CUSTOMER.name(),
                                    Map.of(0, State.MAIN_MENU.name()),
                                    menus::createCustomer
                ),

                new StateTransition(State.COMPANIES_LIST.name(),
                                    Map.of(0, State.LOGIN_AS_MANAGER.name(),
                                           1, State.COMPANY_MENU.name()),
                                    menus::companiesListChoice
                ),

                new StateTransition(State.COMPANY_MENU.name(),
                                    Map.of(0, State.LOGIN_AS_MANAGER.name(),
                                           1, State.CAR_LIST.name(),
                                           2, State.CREATE_CAR.name()),
                                    menus::companyMenu
                ),
                new StateTransition(State.CREATE_COMPANY.name(),
                                    Map.of(0, State.LOGIN_AS_MANAGER.name()),
                                    menus::createCompany
                ),

                new StateTransition(State.CAR_LIST.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),
                                    menus::carList
                ),
                new StateTransition(State.CREATE_CAR.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),
                                    menus::createCar
                ),

                new StateTransition(State.CUSTOMER_MENU.name(),
                                    Map.of(1, State.RENT_CAR.name(),
                                           2, State.RETURN_RENTED_CAR.name(),
                                           3, State.RENTED_CAR_VIEW.name(),
                                           0, State.MAIN_MENU.name()),
                                    menus::customerMenu
                ),
                new StateTransition(State.RENT_CAR.name(),
                                    Map.of(0, State.CUSTOMER_MENU.name()),
                                    // TODO: 9/25/21 Change the dummy map for a real one
                                    () -> { System.out.println("\nUnder construction..."); return 0;}
                                    // TODO: 9/25/21 Change the dummy inputGenerator for a real one
                ),
                new StateTransition(State.RETURN_RENTED_CAR.name(),
                                    Map.of(0, State.CUSTOMER_MENU.name()),
                                    // TODO: 9/25/21 Change the dummy map for a real one
                                    () -> { System.out.println("\nUnder construction..."); return 0;}
                                    // TODO: 9/25/21 Change the dummy inputGenerator for a real one
                ),
                new StateTransition(State.RENTED_CAR_VIEW.name(),
                                    Map.of(0, State.CUSTOMER_MENU.name()),
                                    menus::rentedCarView
                ),

                new StateTransition(State.EXIT.name(),
                                    Map.of(0, ""),
                                    () -> 0
                )
        );
        return Collections.unmodifiableMap(
                transList.stream().collect(Collectors.toMap(StateTransition::getStateName, s -> s)));
    }


}
