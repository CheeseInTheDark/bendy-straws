package io.bendystraws.reducer;

import io.bendystraws.action.Action;

import java.util.List;

public class CombinedReducers<S> implements Reducer<S> {
    private List<Reducer<S>> reducers;

    public CombinedReducers(List<Reducer<S>> reducers) {
        this.reducers = reducers;
    }

    public static <S> Reducer<S> combine(List<Reducer<S>> reducers) {
        return new CombinedReducers<>(reducers);
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
