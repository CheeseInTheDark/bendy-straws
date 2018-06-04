package io.bendystraws.test;

import io.bendystraws.action.Action;

public class TestActionWithPayload extends Action<TestPayload> {
    public TestActionWithPayload(TestPayload testPayload) {
        super(testPayload);
    }
}
