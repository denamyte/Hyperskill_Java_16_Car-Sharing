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
        int input = 0;
        while (stateMachine.goingOn()) {
            input = cli.execStateAction(stateMachine.switchState(input));
        }
    }
}
