package carsharing.state;

import java.util.Map;
import java.util.function.IntSupplier;

/**
 * The purpose of this class is to call the function
 * <code>toggleState()</code> that toggles the state of
 * this instance to one of the predefined states,
 * based on user choice.
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
