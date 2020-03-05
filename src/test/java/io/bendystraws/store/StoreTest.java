package io.bendystraws.store;

import io.bendystraws.action.Action;
import io.bendystraws.reducer.Reducer;
import io.bendystraws.test.TestState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class StoreTest {

    private Store<TestState> subject;

    private TestState initialState = new TestState();

    private TestState newState = new TestState();

    @Mock private Reducer<TestState> reducer;

    @Mock private Action<?> action;

    @Mock private Subscription firstSubscriber;

    @Mock private Subscription secondSubscriber;

    @Before
    public void setup() {
        initMocks(this);

        when(reducer.reduce(null, null)).thenReturn(initialState);
        when(reducer.reduce(initialState, action)).thenReturn(newState);

        subject = new Store<>(reducer);
    }

    @Test
    public void returnsInitialStateBeforeAnyDispatchesOccur() {
        assertThat(subject.getState(), is(initialState));
    }

    @Test
    public void returnsNewStateAfterActionDispathc() {
        subject.dispatch(action);

        assertThat(subject.getState(), is(newState));
    }

    @Test
    public void invokesSubscribersWhenActionIsDispatched() {
        subject.subscribe(firstSubscriber);
        subject.subscribe(secondSubscriber);

        subject.dispatch(action);

        verify(firstSubscriber).invoke();
        verify(secondSubscriber).invoke();
    }

    @Test
    public void stopsNotifyingSubscriberAfterSubscriptionIsCanceled() {
        subject.subscribe(firstSubscriber);
        subject.subscribe(secondSubscriber);

        subject.unsubscribe(firstSubscriber);
        subject.dispatch(action);

        verify(firstSubscriber, never()).invoke();
        verify(secondSubscriber).invoke();
    }

    @Test
    public void doesNotNotifySubscribersUntilNewStateIsAvailable() {
        subject.subscribe(firstSubscriber);
        doAnswer(verifyStateUpdated).when(firstSubscriber).invoke();

        subject.dispatch(action);
    }

    private Answer<Void> verifyStateUpdated = new Answer<Void>() {

        @Override
        public Void answer(InvocationOnMock invocation) {
            assertThat(subject.getState(), is(newState));
            return null;
        }
    };

}
