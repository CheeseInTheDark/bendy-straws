package io.bendystraws.action;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ActionTest {

    private Action<TestPayload> subject;

    private class TestPayload {}

    private class TestAction extends Action<TestPayload> {
        public TestAction(TestPayload testPayload) {
            super(testPayload);
        }
    }

    @Mock
    private TestPayload testPayload;

    @Before
    public void setup() {
        subject = new TestAction(testPayload);
    }

    @Test
    public void returnsItsPayload() {
        assertThat(subject.getPayload(), is(testPayload));
    }
}