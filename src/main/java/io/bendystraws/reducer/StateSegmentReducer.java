package io.bendystraws.reducer;

import io.bendystraws.action.Action;

public class StateSegmentReducer<P, S> implements Reducer<P> {
    private StateSegment<P, S> stateSegment;
    private Reducer<S> reducerForSegment;

    public StateSegmentReducer(StateSegment<P, S> stateSegment, Reducer<S> reducerForSegment) {
        this.stateSegment = stateSegment;
        this.reducerForSegment = reducerForSegment;
    }

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
