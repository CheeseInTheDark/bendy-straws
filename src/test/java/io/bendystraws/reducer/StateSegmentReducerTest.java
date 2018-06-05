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

public class StateSegmentReducerTest {

    private Reducer<TestParentState> subject;

    @Mock
    private TestAction testAction;

    @Mock
    private StateSegment<TestParentState, TestState> stateSegment;

    @Mock
    private TestState extractedSegment;

    @Mock
    private TestState newSegment;

    @Mock
    private TestParentState parentState;

    @Mock
    private TestParentState newParentState;

    @Mock
    private Reducer<TestState> underlyingReducer;

    @Before
    public void setup() {
        initMocks(this);

        when(stateSegment.extractFrom(parentState)).thenReturn(extractedSegment);
        when(stateSegment.insert(newSegment, parentState)).thenReturn(newParentState);
        when(underlyingReducer.reduce(extractedSegment, testAction)).thenReturn(newSegment);

        subject = new StateSegmentReducer(stateSegment, underlyingReducer);
    }

    @Test
    public void returnsStateFromUnderlyingReducer() {
        TestParentState newState = subject.reduce(parentState, testAction);

        assertThat(newState, is(newParentState));
    }

    private class TestParentState {}
}
