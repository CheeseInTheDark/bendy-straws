package io.bendystraws.reducer;

import io.bendystraws.action.Action;
import io.bendystraws.test.TestAction;
import io.bendystraws.test.TestPayload;
import io.bendystraws.test.TestState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LeafReducerTest {

    private Reducer<TestState> subject;

    @Mock
    private ActionHandler<TestState, TestAction> firstHandler;

    @Mock
    private ActionHandler<TestState, SecondTestAction> secondHandler;

    private ActionMap<TestState> actionMap;

    private TestState defaultState = new TestState();

    private TestState previousState = new TestState();

    private TestState stateFromFirstHandler = new TestState();
    private TestState stateFromSecondHandler = new TestState();

    @Before
    public void setup() {
        initMocks(this);

        actionMap = new ActionMap<>();
        actionMap.put(TestAction.class, firstHandler);
        actionMap.put(SecondTestAction.class, secondHandler);

        subject = new LeafReducer<>(actionMap, defaultState);

        when(firstHandler.reduce(eq(previousState), any(TestAction.class))).thenReturn(stateFromFirstHandler);
        when(firstHandler.reduce(eq(previousState), any(SecondTestAction.class))).thenReturn(previousState);

        when(secondHandler.reduce(eq(previousState), any(SecondTestAction.class))).thenReturn(stateFromSecondHandler);
        when(secondHandler.reduce(eq(previousState), any(TestAction.class))).thenReturn(previousState);
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

    private class SecondTestAction extends Action<Void> {}
}
