package io.bendystraws.reducer;

import io.bendystraws.action.Action;

import java.util.List;

/**
 * A {@link Reducer} which is comprised of other Reducers.
 * When {@link #reduce} is called, the child Reducers' reduce
 * methods are called in order, and the new state is returned.
 *
 * @param <S> the type of the state
 */
public class CombinedReducers<S> implements Reducer<S> {
    private List<Reducer<S>> reducers;

    /**
     * @param reducers the {@link Reducer}s to combine
     */
    public CombinedReducers(List<Reducer<S>> reducers) {
        this.reducers = reducers;
    }

    /**
     * Convenience method for combining {@link Reducer}s
     *
     * @param reducers the {@link Reducer}s to combine
     * @param <S> the type of the state
     * @return the new {@link CombinedReducers}
     */
    public static <S> Reducer<S> combine(List<Reducer<S>> reducers) {
        return new CombinedReducers<>(reducers);
    }


    /**
     * Calls each child {@link Reducer}'s {@link Reducer#reduce}
     * method in turn, with the updated state from the previous call
     * being used for the next call, until finally the end state is returned
     *
     * @param previousState the previous state
     * @param action the action that is causing the update to occur
     * @return the new state
     */
    @Override
    public S reduce(S previousState, Action<?> action) {
        S state = previousState;
        for (Reducer<S> reducer : reducers) {
            state = reducer.reduce(state, action);
        }
        return state;
    }
}
