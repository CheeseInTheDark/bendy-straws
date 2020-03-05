package io.bendystraws.reducer;

import io.bendystraws.action.Action;
import io.bendystraws.test.TestAction;
import io.bendystraws.test.TestState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LeafReducerTest {

    private Reducer<TestState> subject;

    @Mock private ActionHandler<TestState, TestAction> firstHandler;

    @Mock private ActionHandler<TestState, SecondTestAction> secondHandler;

    private ActionMap<TestState> actionMap;

    private TestState defaultState = new TestState();

    private TestState previousState = new TestState();

    private TestState stateFromFirstHandler = new TestState();
    private TestState stateFromSecondHandler = new TestState();

    @Before
    public void setup() {
        initMocks(this);

        when(firstHandler.getActionClass()).thenReturn(TestAction.class);
        when(firstHandler.reduce(eq(previousState), any(TestAction.class))).thenReturn(stateFromFirstHandler);
        when(firstHandler.reduce(eq(previousState), any(SecondTestAction.class))).thenReturn(previousState);

        when(secondHandler.getActionClass()).thenReturn(SecondTestAction.class);
        when(secondHandler.reduce(eq(previousState), any(SecondTestAction.class))).thenReturn(stateFromSecondHandler);
        when(secondHandler.reduce(eq(previousState), any(TestAction.class))).thenReturn(previousState);

        actionMap = new ActionMap<>(asList(firstHandler, secondHandler));

        subject = new LeafReducer<>(actionMap, defaultState);
    }

    @Test
    public void returnsResultFromCorrespondingHandlerGivenATestAction() {
        TestState state = subject.reduce(previousState, new TestAction());

        assertThat(state, is(stateFromFirstHandler));
    }

    @Test
    public void returnsResultFromCorrespondingHandlerGivenADifferentTestAction() {
        TestState state = subject.reduce(previousState, new SecondTestAction());

        assertThat(state, is(stateFromSecondHandler));
    }

    @Test
    public void returnsDefaultStateWhenNoActionIsProvided() {
        TestState state = subject.reduce(previousState, null);

        assertThat(state, is(defaultState));
    }

    @Test
    public void returnsSameStateWhenNoActionHandlerAppliesToGivenAction() {
        TestState state = subject.reduce(previousState, new NonapplicableAction());

        assertThat(state, is(previousState));
    }

    private static class SecondTestAction extends Action<Void> {}

    private static class NonapplicableAction extends Action<Void> {}
}
