package io.bendystraws.store;

/**
 * Callback for state changes in a {@link Store}
 */
public interface Subscription {
    void invoke();
}
