package io.bendystraws.integration;

import io.bendystraws.action.Action;
import io.bendystraws.reducer.ActionHandler;
import io.bendystraws.reducer.ActionMap;
import io.bendystraws.reducer.LeafReducer;
import io.bendystraws.store.Store;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleTest {

    private static class IntegrationTestAction extends Action<Void> {}
    private static class IntegrationTestState {
        String value = "Goodbye";
    }

    @Test
    public void canAlterStateByDispatchingActions() {
        ActionMap<IntegrationTestState> actions = new ActionMap<>(singletonList(
                new ActionHandler<>(IntegrationTestAction.class, (previousState, action) -> {
                    previousState.value = "Hello";
                    return previousState;
                }))
        );

        Store<IntegrationTestState> subject = new Store<>(new LeafReducer<>(actions, new IntegrationTestState()));

        assertThat(subject.getState().value, is("Goodbye"));

        subject.dispatch(new IntegrationTestAction());

        assertThat(subject.getState().value, is("Hello"));
    }
}
