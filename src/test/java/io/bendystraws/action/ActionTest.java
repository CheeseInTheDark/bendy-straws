package io.bendystraws.action;

import io.bendystraws.test.TestAction;
import io.bendystraws.test.TestPayload;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ActionTest {

    private Action<TestPayload> subject;

    @Mock
    private TestPayload testPayload;

    @Before
    public void setup() {
        subject = new TestActionWithPayload(testPayload);
    }

    @Test
    public void returnsItsPayload() {
        assertThat(subject.getPayload(), is(testPayload));
    }

    private class TestActionWithPayload extends Action<TestPayload> {
        public TestActionWithPayload(TestPayload payload) {
            super(payload);
        }
    }
}