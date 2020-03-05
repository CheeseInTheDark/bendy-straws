package io.bendystraws.reducer;

import io.bendystraws.test.TestAction;
import io.bendystraws.test.TestActionWithPayload;
import io.bendystraws.test.TestState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ActionMapTest {

    private ActionMap<TestState> subject;

    @Mock private ActionHandler<TestState, TestAction> firstActionHandler;
    @Mock private ActionHandler<TestState, TestActionWithPayload> secondActionHandler;

    @Before
    public void setUp() {
        initMocks(this);

        when(firstActionHandler.getActionClass()).thenReturn(TestAction.class);
        when(secondActionHandler.getActionClass()).thenReturn(TestActionWithPayload.class);

        subject = new ActionMap<>(asList(firstActionHandler, secondActionHandler));
    }

    @Test
    public void providesActionsItWasGivenBasedOnActionClass() {
        assertThat(subject.get(TestAction.class), is(firstActionHandler));
        assertThat(subject.get(TestActionWithPayload.class), is(secondActionHandler));
    }
}