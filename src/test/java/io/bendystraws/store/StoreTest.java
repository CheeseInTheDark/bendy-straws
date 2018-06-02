package io.bendystraws.store;

import io.bendystraws.reducer.Reducer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class StoreTest {

    private Store<String> subject;

    private String initialState = "initial state";

    @Mock
    private Reducer<String> reducer;

    @Before
    public void setup() {
        initMocks(this);

        when(reducer.reduce()).thenReturn(initialState);

        subject = new Store(reducer);
    }

    @Test
    public void returnsInitialStateBeforeAnyActionsDispatched() {
        assertThat(subject.getState(), is(initialState));
    }
}
