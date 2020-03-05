package io.bendystraws.store;

import io.bendystraws.action.Action;
import io.bendystraws.reducer.Reducer;

import java.util.ArrayList;
import java.util.List;

/**
 * A Store stores the state of an application, updates it when actions are dispatched based
 * on {@link Reducer}s it's built with, and notifies subscribers when the state has changed
 *
 * @param <S> The type of the store's state object
 */
public class Store<S> {
    private Reducer<S> reducer;

    private S state;
    private List<Subscription> subscribers = new ArrayList<>();

    /**
     * @param reducer The {@link Reducer} which is used to update the state when an {@link Action} is dispatched
     */
    public Store(Reducer<S> reducer) {
        this.reducer = reducer;
        this.state = reducer.reduce(null, null);
    }

    /**
     * @return the current state
     */
    public S getState() {
        return state;
    }

    /**
     * Updates the store's state.  The update behavior is defined by the {@link Reducer}
     * passed when the {@link Store} is constructed.
     *
     * @param action the {@link Action} to dispatch
     */
    public void dispatch(Action<?> action) {
        state = reducer.reduce(state, action);
        callbackSubscribers();
    }

    private void callbackSubscribers() {
        for (Subscription subscription : subscribers) {
            subscription.invoke();
        }
    }

    /**
     * Adds a new {@link Subscription} callback to be invoked
     * whenever the store's state is updated
     *
     * @param subscriber callback to invoke
     */
    public void subscribe(Subscription subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * Removes the given {@link Subscription} from the subscribers
     * which are invoked when the store's state is updated
     *
     * @param subscriber callback to remove
     */
    public void unsubscribe(Subscription subscriber) {
        subscribers.remove(subscriber);
    }
}
