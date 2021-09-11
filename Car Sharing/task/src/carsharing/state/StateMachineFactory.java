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
        MANAGER_MENU,

        LOAD_COMPANY_LIST,
        EMPTY_COMPANY_LIST,
        CHOOSE_COMPANY_MENU,
        COMPANY_MENU,
        CREATE_COMPANY,

        LOAD_CAR_LIST,
        EMPTY_CAR_LIST,
        CAR_LIST,
        CREATE_CAR,

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
                                    Map.of(1, State.LOAD_COMPANY_LIST.name(),
                                           2, State.CREATE_COMPANY.name(),
                                           0, State.MAIN_MENU.name()),
                                    menus::managerMenu
                ),
                new StateTransition(State.LOAD_COMPANY_LIST.name(),
                                    Map.of(0, State.EMPTY_COMPANY_LIST.name(),
                                           1, State.CHOOSE_COMPANY_MENU.name()),
                                    menus::loadCompanyList
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
                                    Map.of(0, State.MANAGER_MENU.name(),
                                           1, State.LOAD_CAR_LIST.name(),
                                           2, State.CREATE_CAR.name()),
                                    menus::companyMenu
                ),
                new StateTransition(State.CREATE_COMPANY.name(),
                                    Map.of(0, State.MANAGER_MENU.name()),
                                    menus::createCompany
                ),

                new StateTransition(State.LOAD_CAR_LIST.name(),
                                    Map.of(0, State.EMPTY_CAR_LIST.name(),
                                           1, State.CAR_LIST.name()),
                                    menus::loadCarList
                ),
                new StateTransition(State.EMPTY_CAR_LIST.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),
                                    menus::emptyCarList
                ),
                new StateTransition(State.CAR_LIST.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),
                                    menus::carList
                ),
                new StateTransition(State.CREATE_CAR.name(),
                                    Map.of(0, State.COMPANY_MENU.name()),
                                    menus::createCar
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
