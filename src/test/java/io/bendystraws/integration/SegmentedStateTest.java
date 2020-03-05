package io.bendystraws.integration;

import io.bendystraws.action.Action;
import io.bendystraws.reducer.*;
import io.bendystraws.store.Store;
import org.junit.Test;

import static io.bendystraws.reducer.ActionHandler.handlerFor;
import static io.bendystraws.reducer.ActionMap.handlers;
import static io.bendystraws.reducer.CombinedReducers.combine;
import static io.bendystraws.reducer.LeafReducer.reducer;
import static io.bendystraws.reducer.StateSegmentReducer.segment;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class SegmentedStateTest {

    private static class State {
        FirstSegment firstSegment;
        SecondSegment secondSegment;
    }

    private static class FirstSegment {
        String value;
    }

    private static class SecondSegment {
        String value;
    }

    private static class FirstAction extends Action<Void> {}
    private static class SecondAction extends Action<Void> {}

    private static class FirstStateSegment implements StateSegment<State, FirstSegment> {

        @Override
        public FirstSegment extractFrom(State parentState) {
            return parentState.firstSegment;
        }

        @Override
        public State insert(FirstSegment newSegment, State parentState) {
            parentState.firstSegment = newSegment;
            return parentState;
        }

        @Override
        public State insertIntoNewParent(FirstSegment newSegment) {
            State parent = new State();
            parent.firstSegment = newSegment;
            return parent;
        }
    }

    private static class SecondStateSegment implements StateSegment<State, SecondSegment> {

        @Override
        public SecondSegment extractFrom(State parentState) {
            return parentState.secondSegment;
        }

        @Override
        public State insert(SecondSegment newSegment, State parentState) {
            parentState.secondSegment = newSegment;
            return parentState;
        }

        @Override
        public State insertIntoNewParent(SecondSegment newSegment) {
            State parent = new State();
            parent.secondSegment = newSegment;
            return parent;
        }
    }

    @Test
    public void stateCanBeSegmented() {
        Store<State> subject = new Store<>(
            combine(asList(
                segment(new FirstStateSegment(), reducer(
                    handlers(singletonList(
                        handlerFor(FirstAction.class, (previousState, action) -> {
                            previousState.value = "First";
                            return previousState;
                        })
                    )), new FirstSegment()
                )),
                segment(new SecondStateSegment(), reducer(
                    handlers(singletonList(
                        handlerFor(SecondAction.class, (previousState, action) -> {
                            previousState.value = "Second";
                            return previousState;
                        })
                    )), new SecondSegment()
                )))
            ));

        assertThat(subject.getState().firstSegment.value, is(nullValue()));
        assertThat(subject.getState().secondSegment.value, is(nullValue()));

        subject.dispatch(new FirstAction());
        subject.dispatch(new SecondAction());

        assertThat(subject.getState().firstSegment.value, is("First"));
        assertThat(subject.getState().secondSegment.value, is("Second"));
    }
}
