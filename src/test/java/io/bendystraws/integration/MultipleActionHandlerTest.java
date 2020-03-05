package io.bendystraws.integration;

import io.bendystraws.action.Action;
import io.bendystraws.reducer.ActionHandler;
import io.bendystraws.reducer.ActionMap;
import io.bendystraws.reducer.LeafReducer;
import io.bendystraws.store.Store;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MultipleActionHandlerTest {

    private static class SayHello extends Action<Void> {}
    private static class SayHowdy extends Action<Void> {}

    private static class IntegrationTestState {
        String value = "Goodbye";
    }

    @Test
    public void canAlterStateByDispatchingActionsOfDifferentTypes() {
        ActionMap<IntegrationTestState> actions = new ActionMap<>(asList(
            new ActionHandler<>(SayHello.class, (previousState, action) -> {
                previousState.value = "Hello";
                return previousState;
            }),
            new ActionHandler<>(SayHowdy.class, (previousState, action) -> {
                previousState.value = "Howdy";
                return previousState;
            })
        ));

        Store<IntegrationTestState> subject = new Store<>(new LeafReducer<>(actions, new IntegrationTestState()));

        assertThat(subject.getState().value, is("Goodbye"));

        subject.dispatch(new SayHello());
        subject.dispatch(new SayHowdy());

        assertThat(subject.getState().value, is("Howdy"));
    }
}
