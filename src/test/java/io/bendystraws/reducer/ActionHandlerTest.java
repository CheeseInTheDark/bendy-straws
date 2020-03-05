package io.bendystraws.reducer;

import io.bendystraws.action.Action;
import io.bendystraws.test.TestAction;
import io.bendystraws.test.TestActionWithPayload;
import io.bendystraws.test.TestState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ActionHandlerTest {
    private ActionHandler<TestState, TestActionWithPayload> subject;

    @Mock private TestActionWithPayload testAction;

    @Mock private NonapplicableAction nonapplicableAction;

    @Mock private ActionHandler.Implementation<TestState, TestActionWithPayload> actionHandlerForType;

    @Mock private TestState previousState;

    @Mock private TestState stateFromHandlerImplementation;

    @Before
    public void setup() {
        initMocks(this);

        subject = new ActionHandler<>(TestActionWithPayload.class, actionHandlerForType);

        when(actionHandlerForType.reduce(previousState, testAction)).thenReturn(stateFromHandlerImplementation);
    }

    @Test
    public void returnsUnchangedStateWhenActionIsNotHandled() {
        TestState newState = subject.reduce(previousState, nonapplicableAction);

        assertThat(newState, is(sameInstance(previousState)));
    }

    @Test
    public void returnsStateFromImplementationWhenActionIsHandled() {
        TestState newState = subject.reduce(previousState, testAction);

        assertThat(newState, is(stateFromHandlerImplementation));
    }

    @Test
    public void returnsActionTypeWhichItAppliesTo() {
        assertThat(subject.getActionClass(), org.hamcrest.CoreMatchers.<Class<TestActionWithPayload>>is(TestActionWithPayload.class));
    }

    private class NonapplicableAction extends Action<Void> {}
}