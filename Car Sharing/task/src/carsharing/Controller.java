package carsharing;

public class Controller {

    private final CarSharingCLI cli;
    private final StateMachine stateMachine;

    public Controller(CarSharingCLI cli,
                      StateMachine stateMachine) {
        this.cli = cli;
        this.stateMachine = stateMachine;
    }

    public void programLoop() {
        State state = stateMachine.changeState(0);
        while (state != State.EXIT) {
            final int input = cli.execStateAction(state);
            state = stateMachine.changeState(input);
        }
    }
}
