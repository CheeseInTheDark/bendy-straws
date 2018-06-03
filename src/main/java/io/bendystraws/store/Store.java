package io.bendystraws.store;

import io.bendystraws.action.Action;
import io.bendystraws.reducer.Reducer;

import java.util.ArrayList;
import java.util.List;

public class Store<S> {
    private Reducer<S> reducer;

    private S state;
    private List<Subscription> subscribers = new ArrayList<>();

    public Store(Reducer<S> reducer) {
        this.reducer = reducer;
        this.state = reducer.reduce(null, null);
    }

    public S getState() {
        return state;
    }

    public void dispatch(Action<?> action) {
        state = reducer.reduce(state, action);
        callbackSubscribers();
    }

    private void callbackSubscribers() {
        for (Subscription subscription : subscribers) {
            subscription.invoke();
        }
    }

    public void subscribe(Subscription subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscription subscriber) {
        subscribers.remove(subscriber);
    }
}
