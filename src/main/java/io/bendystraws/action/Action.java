package io.bendystraws.action;

public abstract class Action<P> {
    private final P payload;

    public Action(P payload) {
        this.payload = payload;
    }

    public P getPayload() {
        return payload;
    }
}
