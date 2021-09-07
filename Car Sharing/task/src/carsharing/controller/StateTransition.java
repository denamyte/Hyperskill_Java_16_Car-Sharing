package carsharing.controller;

import java.util.Map;
import java.util.function.IntSupplier;

/**
 * This class:
 * <ul>
 *     <li>contains the description of the current state,</li>
 *     <li>executes the action of the current state,</li>
 *     <li>outputs the next state designation</li>
 * </ul>
 */
public class StateTransition {

    private final String stateIndex;
    //                input    next state name
    private final Map<Integer, String> inputToStateMap;
    private final IntSupplier inputGenerator;
    private final String description;

    public StateTransition(String stateIndex,
                           Map<Integer, String> inputToStateMap,
                           IntSupplier inputGenerator,
                           String description) {
        this.stateIndex = stateIndex;
        this.inputToStateMap = inputToStateMap;
        this.inputGenerator = inputGenerator;
        this.description = description;
    }

    public String toggleState() {
        return inputToStateMap.get(inputGenerator.getAsInt());
    }

    public String getDescription() {
        return description;
    }
}
