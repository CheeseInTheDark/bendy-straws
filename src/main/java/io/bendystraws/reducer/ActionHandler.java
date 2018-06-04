package io.bendystraws.reducer;

import io.bendystraws.action.Action;

public class ActionHandler<S, A extends Action<?>> implements Reducer<S> {
    @Override
    public S reduce(S previousState, Action<?> action) {
        return null;
    }
}
