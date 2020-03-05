package io.bendystraws.reducer;

import io.bendystraws.test.TestAction;
import io.bendystraws.test.TestState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CombinedReducersTest {

    private Reducer<TestState> subject;

    @Mock private Reducer<TestState> firstReducer;

    @Mock private Reducer<TestState> secondReducer;

    @Mock private Reducer<TestState> thirdReducer;

    @Mock private TestState state;

    @Mock private TestAction action;

    @Mock private TestState stateFromFirstReducer;

    @Mock private TestState stateFromSecondReducer;

    @Mock private TestState stateFromThirdReducer;

    @Before
    public void setup() {
        initMocks(this);

        when(firstReducer.reduce(state, action)).thenReturn(stateFromFirstReducer);
        when(secondReducer.reduce(stateFromFirstReducer, action)).thenReturn(stateFromSecondReducer);
        when(thirdReducer.reduce(stateFromSecondReducer, action)).thenReturn(stateFromThirdReducer);
    }

    @Test
    public void returnsStateFromTwoCombinedReducers() {
        subject = new CombinedReducers(firstReducer, secondReducer);

        TestState newState = subject.reduce(state, action);

        assertThat(newState, is(stateFromSecondReducer));
    }


}
