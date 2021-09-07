package carsharing.controller;

import carsharing.CarSharingCLI;

import java.util.Map;

/**
 * Contains all settings for the state machine to work
 */
public class StateMachineFactory {

    private CarSharingCLI cli;

    enum State {
        INITIAL,
        MAIN_MENU,
        MANAGER_MENU,

        GET_COMPANY_LIST,
        EMPTY_COMPANY_LIST,
        CHOOSE_COMPANY_MENU,
        COMPANY_MENU,

        CREATE_COMPANY,
        EXIT
    }

    private Map<String, StateTransition> transitionMap = Map.of(
            State.INITIAL.name(), new StateTransition(
                    State.INITIAL.name(),
                    Map.of(0, State.MAIN_MENU.name()),
                    () -> 0,
                    "Does nothing except toggling the Main Menu state"
            ),
            State.MAIN_MENU.name(), new StateTransition(
                    State.MAIN_MENU.name(),
                    Map.of(1, State.MANAGER_MENU.name(),
                           0, State.EXIT.name()),
                    cli::mainMenu, ""
            ),
            State.MANAGER_MENU.name(), new StateTransition(
                    State.MANAGER_MENU.name(),
                    Map.of(1, State.GET_COMPANY_LIST.name(),
                           2, State.CREATE_COMPANY.name(),
                           0, State.MAIN_MENU.name()),
                    cli::managerMenu, ""
            ),
            State.GET_COMPANY_LIST.name(), new StateTransition(
                    State.GET_COMPANY_LIST.name(),
                    Map.of(0, State.EMPTY_COMPANY_LIST.name(),
                           1, State.CHOOSE_COMPANY_MENU.name()),
                    cli::getCompanyList, ""
            ),
            State.EMPTY_COMPANY_LIST.name(), new StateTransition(
                    State.EMPTY_COMPANY_LIST.name(),
                    Map.of(0, State.MANAGER_MENU.name()),
                    cli::emptyCompanyList, ""
            ),
            State.CHOOSE_COMPANY_MENU.name(), new StateTransition(
                    State.CHOOSE_COMPANY_MENU.name(),
                    Map.of(0, State.MANAGER_MENU.name(),
                           1, State.COMPANY_MENU.name()),
                    cli::chooseCompanyMenu, ""
            ),
            State.COMPANY_MENU.name(), new StateTransition(
                    State.COMPANY_MENU.name(),
                    Map.of(0, State.MANAGER_MENU.name()),

                    // TODO: 9/7/21 Implement company menu next

                    () -> 0, ""
            ),
            State.CREATE_COMPANY.name(), new StateTransition(
                    State.CREATE_COMPANY.name(),
                    Map.of(0, State.MANAGER_MENU.name()),
                    cli::createCompany, ""
            )

    );

    public StateMachineFactory(CarSharingCLI cli) {
        this.cli = cli;
    }
}
