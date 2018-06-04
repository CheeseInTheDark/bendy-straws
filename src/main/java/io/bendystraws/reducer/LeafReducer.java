package io.bendystraws.reducer;

import io.bendystraws.action.Action;

public class LeafReducer<S> implements Reducer<S> {
    private ActionMap<S> actionHandlers;
    private S defaultState;

    public LeafReducer(ActionMap<S> actionHandlers, S defaultState) {
        this.actionHandlers = actionHandlers;
        this.defaultState = defaultState;
    }

    @Override
    public S reduce(S previousState, Action<?> action) {
        if (action == null) { return defaultState; }

        return actionHandlers.get(action.getClass()).reduce(previousState, action);
    }
}
