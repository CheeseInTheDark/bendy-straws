package io.bendystraws.reducer;

import io.bendystraws.action.Action;

import java.util.List;

import static java.util.Arrays.asList;

public class CombinedReducers<S> implements Reducer<S> {
    private List<Reducer<S>> reducers;

    public CombinedReducers(Reducer<S>... reducers) {
        this.reducers = asList(reducers);
    }

    @Override
    public S reduce(S previousState, Action<?> action) {
        S state = previousState;
        for (Reducer<S> reducer : reducers) {
            state = reducer.reduce(state, action);
        }
        return state;
    }
}
