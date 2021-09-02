package carsharing;

public class StateMachine {

    private State state = State.INITIAL;

    public State switchState(int input) {
        switch (state) {

            case INITIAL:
                state = State.MAIN_MENU;
                break;
            case MAIN_MENU:
                state = input == 1 ? State.MANAGER_MENU : State.EXIT;
                break;
            case MANAGER_MENU:
                state = input == 1 ? State.GET_COMPANY_LIST
                        : input == 2 ? State.CREATE_COMPANY
                        : State.MAIN_MENU;
                break;
            case GET_COMPANY_LIST:
                state = input == 0 ? State.EMPTY_COMPANY_LIST
                        : State.CHOOSE_COMPANY_MENU;
                break;
            case EMPTY_COMPANY_LIST:
                state = State.MANAGER_MENU;
                break;
            case CHOOSE_COMPANY_MENU:
                state = input == 0 ? State.MANAGER_MENU
                        : State.COMPANY_MENU;
                break;
            case COMPANY_MENU:
                state = State.MANAGER_MENU;
                // TODO: 9/3/21 Implement manager menu next
                break;

            case CREATE_COMPANY:
                state = State.MANAGER_MENU;
                break;
            case EXIT:
                break;
        }
        return state;
    }

    public boolean goingOn() {
        return state != State.EXIT;
    }
}
