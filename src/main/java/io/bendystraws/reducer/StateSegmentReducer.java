package io.bendystraws.reducer;

import io.bendystraws.action.Action;

/**
 * A {@link Reducer} which manages a specific part of a larger state
 *
 * @param <P> Type of the parent state
 * @param <S> Type of the child state handled by this reducer
 */
public class StateSegmentReducer<P, S> implements Reducer<P> {
    private StateSegment<P, S> stateSegment;
    private Reducer<S> reducerForSegment;

    /**
     * @param stateSegment defines how the child state is extracted from and put back into the parent once
     *                     the child state has been reduced
     * @param reducerForSegment the actual reducer that produces the new child state
     */
    public StateSegmentReducer(StateSegment<P, S> stateSegment, Reducer<S> reducerForSegment) {
        this.stateSegment = stateSegment;
        this.reducerForSegment = reducerForSegment;
    }

    /**
     * Convenience method for producing a new {@link StateSegmentReducer}
     *
     * @param stateSegment defines how the child state is extracted from and put back into the parent once
     * @param reducerForSegment the actual reducer that produces the new child state
     * @param <P> Type of the parent state
     * @param <S> Type of the child state handled by this reducer
     * @return the new StateSegmentReducer
     */
    public static <P, S> Reducer<P> segment(StateSegment<P, S> stateSegment, Reducer<S> reducerForSegment) {
        return new StateSegmentReducer<>(stateSegment, reducerForSegment);
    }

    /**
     * Extracts the child state from the parent state, passes the child state and the
     * action to the reducer this {@link StateSegmentReducer} was built with, and
     * reincorporates the new child state into the parent.  If the initial parent state is
     * null, a new parent state is created.
     *
     * @param previousState the previous state
     * @param action the {link @Action} which is causing the state update
     * @return the parent state with updated child state
     */
    @Override
    public P reduce(P previousState, Action<?> action) {
        if (previousState == null) {
            return stateSegment.insertIntoNewParent(reducerForSegment.reduce(null, action));
        }

        S segmentedState = stateSegment.extractFrom(previousState);
        S newSegmentedState = reducerForSegment.reduce(segmentedState, action);
        return stateSegment.insert(newSegmentedState, previousState);
    }
}
