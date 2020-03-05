package io.bendystraws.integration;

import io.bendystraws.action.Action;
import io.bendystraws.reducer.ActionHandler;
import io.bendystraws.reducer.ActionMap;
import io.bendystraws.reducer.LeafReducer;
import io.bendystraws.store.Store;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class PayloadActionTest {

    private static class DeliverPackage extends Action<String> {
        DeliverPackage(String deliveredPackage) {
            super(deliveredPackage);
        }
    }

    private static class IntegrationTestState {
        List<String> deliveredPackages = new ArrayList<>();
    }

    @Test
    public void canAlterStateByDispatchingActionsOfDifferentTypes() {
        ActionMap<IntegrationTestState> actions = new ActionMap<>(singletonList(
                new ActionHandler<>(DeliverPackage.class, (previousState, action) -> {
                    previousState.deliveredPackages.add(action.getPayload());
                    return previousState;
                })
        ));

        Store<IntegrationTestState> subject = new Store<>(new LeafReducer<>(actions, new IntegrationTestState()));

        assertThat(subject.getState().deliveredPackages, is(empty()));

        subject.dispatch(new DeliverPackage("Shrimp"));

        assertThat(subject.getState().deliveredPackages, containsInAnyOrder("Shrimp"));
    }

}
