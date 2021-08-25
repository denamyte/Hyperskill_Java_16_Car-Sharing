package carsharing;

public class StateMachine {

    private State state = State.INITIAL;

    public State switchState(int input) {
        switch (state) {

            case INITIAL:
                state = State.MAIN_MENU;
                break;
            case MAIN_MENU:
                state = input == 1 ? State.LOGGED_AS_MANAGER : State.EXIT;
                break;
            case LOGGED_AS_MANAGER:
                state = input == 1 ? State.COMPANY_LIST
                        : input == 2 ? State.CREATE_COMPANY
                        : State.MAIN_MENU;
                break;
            case COMPANY_LIST:
            case CREATE_COMPANY:
                state = State.LOGGED_AS_MANAGER;
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
