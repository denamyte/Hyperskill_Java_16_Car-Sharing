package carsharing.state;

import carsharing.CarSharingCLI;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains all settings for the state machine to work
 */
public class StateMachineFactory {

    private CarSharingCLI cli;

    enum State {
        MAIN_MENU,
        MANAGER_MENU,

        GET_COMPANY_LIST,
        EMPTY_COMPANY_LIST,
        CHOOSE_COMPANY_MENU,
        COMPANY_MENU,

        GET_CAR_LIST,
        EMPTY_CAR_LIST,
        CAR_LIST,
        CREATE_CAR,

        CREATE_COMPANY,
        EXIT
    }

    public StateMachineFactory(CarSharingCLI cli) {
        this.cli = cli;
    }

    public StateMachineController createStateMachineController() {
        return new StateMachineController(createTransitionMap(), State.MAIN_MENU.name());
    }

    private Map<String, StateTransition> createTransitionMap() {
        List<StateTransition> transList = List.of(
                new StateTransition(State.MAIN_MENU.name(),
                                    Map.of(1, State.MANAGER_MENU.name(),
                                           0, State.EXIT.name()),
                                    cli::mainMenu
                ),
                new StateTransition(State.MANAGER_MENU.name(),
                                    Map.of(1, State.GET_COMPANY_LIST.name(),
                                           2, State.CREATE_COMPANY.name(),
                                           0, State.MAIN_MENU.name()),
                                    cli::managerMenu
                ),
                new StateTransition(State.GET_COMPANY_LIST.name(),
                                    Map.of(0, State.EMPTY_COMPANY_LIST.name(),
                                           1, State.CHOOSE_COMPANY_MENU.name()),
                                    cli::getCompanyList
                ),
                new StateTransition(State.EMPTY_COMPANY_LIST.name(),
                                    Map.of(0, State.MANAGER_MENU.name()),
                                    cli::emptyCompanyList
                ),
                new StateTransition(State.CHOOSE_COMPANY_MENU.name(),
                                    Map.of(0, State.MANAGER_MENU.name(),
                                           1, State.COMPANY_MENU.name()),
                                    cli::chooseCompanyMenu
                ),
                new StateTransition(State.COMPANY_MENU.name(),
                                    Map.of(0, State.CHOOSE_COMPANY_MENU.name(),
                                           1, State.GET_CAR_LIST.name(),
                                           2, State.CREATE_CAR.name()),
                                    cli::companyMenu
                ),

                new StateTransition(State.GET_CAR_LIST.name(),
                                    Map.of(0, State.EMPTY_CAR_LIST.name(),
                                           1, State.CAR_LIST.name()),
                                    cli::getCarList
                ),
                new StateTransition(State.EMPTY_CAR_LIST.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),
                                    cli::emptyCarList
                ),
                new StateTransition(State.CAR_LIST.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),

                                    // TODO: 9/9/21 To be implemented

                                    cli::carList
                ),
                new StateTransition(State.CREATE_CAR.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),

                                    // TODO: 9/9/21 To be implemented

                                    cli::createCar
                ),

                new StateTransition(State.CREATE_COMPANY.name(),
                                    Map.of(0, State.MANAGER_MENU.name()),
                                    cli::createCompany
                ),


                new StateTransition(State.EXIT.name(),
                                    Map.of(0, ""),
                                    () -> 0
                )
        );
        return Collections.unmodifiableMap(
                transList.stream().collect(Collectors.toMap(StateTransition::getStateIndex, s -> s)));
    }


}
