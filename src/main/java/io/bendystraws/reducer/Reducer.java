package io.bendystraws.reducer;

import io.bendystraws.action.Action;

/**
 * A Reducer returns a new state given a previous state and an {@link Action}
 *
 * <p>
 * This interface can be implemented directly, or the included implementations can be used:
 *
 * {@link LeafReducer}
 * {@link CombinedReducers}
 * {@link StateSegmentReducer}
 * </p>
 *
 * @param <S> Class representing the state used by the reducer
 */
public interface Reducer<S> {
    S reduce(S previousState, Action<?> action);
}
