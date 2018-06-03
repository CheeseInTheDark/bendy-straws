package io.bendystraws.reducer;

import io.bendystraws.action.Action;

public interface Reducer<S> {
    S reduce(S previousState, Action<?> action);
}
