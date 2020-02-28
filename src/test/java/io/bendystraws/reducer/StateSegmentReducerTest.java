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

    @Mock private TestAction testAction;
    @Mock private StateSegment<TestParentState, TestState> stateSegment;
    @Mock private TestState extractedSegment;
    @Mock private TestState updatedSegment;
    @Mock private TestState newSegment;
    @Mock private TestParentState newParentState;
    @Mock private TestParentState parentState;
    @Mock private TestParentState updatedParentState;
    @Mock private Reducer<TestState> underlyingReducer;

    @Before
    public void setup() {
        initMocks(this);

        when(stateSegment.extractFrom(parentState)).thenReturn(extractedSegment);
        when(stateSegment.insert(updatedSegment, parentState)).thenReturn(updatedParentState);
        when(underlyingReducer.reduce(extractedSegment, testAction)).thenReturn(updatedSegment);

        when(stateSegment.insertIntoNewParent(newSegment)).thenReturn(newParentState);
        when(underlyingReducer.reduce(null, testAction)).thenReturn(newSegment);

        subject = new StateSegmentReducer<>(stateSegment, underlyingReducer);
    }

    @Test
    public void returnsStateFromUnderlyingReducer() {
        TestParentState newState = subject.reduce(parentState, testAction);

        assertThat(newState, is(updatedParentState));
    }

    @Test
    public void returnsNewStateObjectWhenPreviousStateIsNull() {
        TestParentState newState = subject.reduce(null, testAction);

        assertThat(newState, is(newParentState));
    }

    private class TestParentState {}
}
