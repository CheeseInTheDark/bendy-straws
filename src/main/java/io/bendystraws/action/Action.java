package io.bendystraws.action;

import io.bendystraws.store.Store;

/**
 * Actions are passed to the {@link Store#dispatch} method to update the
 * {@link Store}'s state.  They can contain a payload.
 *
 * @param <P> the type of the payload
 */
public abstract class Action<P> {
    private final P payload;

    /**
     * No-payload constructor
     */
    public Action() {
        this.payload = null;
    }

    /**
     * @param payload data to include with the action
     */
    public Action(P payload) {
        this.payload = payload;
    }

    /**
     * @return this action's payload
     */
    public P getPayload() {
        return payload;
    }
}
