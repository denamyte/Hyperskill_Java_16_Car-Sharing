package carsharing.state.model;

import java.util.Map;

public class StateMachineController {
    private final Map<String, StateTransition> transitionMap;
    private String state;

    public StateMachineController(Map<String, StateTransition> transitionMap, String startingStateName) {
        this.transitionMap = transitionMap;
        this.state = startingStateName;
    }

    public void run() {
        while (state != null && !state.isEmpty()) {
            state = transitionMap.get(state).toggleState();
        }
    }
}
