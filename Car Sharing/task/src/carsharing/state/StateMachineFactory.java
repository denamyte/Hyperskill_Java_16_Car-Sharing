package carsharing.state;

import carsharing.CarSharingMenus;
import carsharing.state.model.StateMachineController;
import carsharing.state.model.StateTransition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains all settings for the state machine to work
 */
public class StateMachineFactory {

    private CarSharingMenus menus;

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

    public StateMachineFactory(CarSharingMenus menus) {
        this.menus = menus;
    }

    public StateMachineController createStateMachineController() {
        return new StateMachineController(createTransitionMap(), State.MAIN_MENU.name());
    }

    private Map<String, StateTransition> createTransitionMap() {
        List<StateTransition> transList = List.of(
                new StateTransition(State.MAIN_MENU.name(),
                                    Map.of(1, State.MANAGER_MENU.name(),
                                           0, State.EXIT.name()),
                                    menus::mainMenu
                ),
                new StateTransition(State.MANAGER_MENU.name(),
                                    Map.of(1, State.GET_COMPANY_LIST.name(),
                                           2, State.CREATE_COMPANY.name(),
                                           0, State.MAIN_MENU.name()),
                                    menus::managerMenu
                ),
                new StateTransition(State.GET_COMPANY_LIST.name(),
                                    Map.of(0, State.EMPTY_COMPANY_LIST.name(),
                                           1, State.CHOOSE_COMPANY_MENU.name()),
                                    menus::getCompanyList
                ),
                new StateTransition(State.EMPTY_COMPANY_LIST.name(),
                                    Map.of(0, State.MANAGER_MENU.name()),
                                    menus::emptyCompanyList
                ),
                new StateTransition(State.CHOOSE_COMPANY_MENU.name(),
                                    Map.of(0, State.MANAGER_MENU.name(),
                                           1, State.COMPANY_MENU.name()),
                                    menus::chooseCompanyMenu
                ),
                new StateTransition(State.COMPANY_MENU.name(),
                                    Map.of(0, State.CHOOSE_COMPANY_MENU.name(),
                                           1, State.GET_CAR_LIST.name(),
                                           2, State.CREATE_CAR.name()),
                                    menus::companyMenu
                ),

                new StateTransition(State.GET_CAR_LIST.name(),
                                    Map.of(0, State.EMPTY_CAR_LIST.name(),
                                           1, State.CAR_LIST.name()),
                                    menus::getCarList
                ),
                new StateTransition(State.EMPTY_CAR_LIST.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),
                                    menus::emptyCarList
                ),
                new StateTransition(State.CAR_LIST.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),

                                    // TODO: 9/9/21 To be implemented

                                    menus::carList
                ),
                new StateTransition(State.CREATE_CAR.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),

                                    // TODO: 9/9/21 To be implemented

                                    menus::createCar
                ),

                new StateTransition(State.CREATE_COMPANY.name(),
                                    Map.of(0, State.MANAGER_MENU.name()),
                                    menus::createCompany
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
