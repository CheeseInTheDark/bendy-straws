package io.bendystraws.reducer;

import io.bendystraws.action.Action;

/**
 * A Reducer takes a state and an action, and returns a new state based on the action.
 *
 * <p>
 * This interface can be implemented directly, or the included implementations can be used:
 *
 * {@link LeafReducer}
 * {@link CombinedReducers}
 * {@link StateSegmentReducer}
 *
 * @param <S> Class representing the state used by the reducer
 * </p>
 */
public interface Reducer<S> {
    S reduce(S previousState, Action<?> action);
}
