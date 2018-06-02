package io.bendystraws.store;

import io.bendystraws.reducer.Reducer;

public class Store<S> {
    private Reducer<S> reducer;

    public Store(Reducer<S> reducer) {
        this.reducer = reducer;
    }

    public S getState() {
        return reducer.reduce();
    }
}
