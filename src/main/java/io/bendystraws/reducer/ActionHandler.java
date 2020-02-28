package io.bendystraws.reducer;

import io.bendystraws.action.Action;

public class ActionHandler<S, A extends Action<?>> implements Reducer<S> {
    private Class<A> actionClass;
    private Implementation<S, A> actionHandlerForType;

    public ActionHandler(Class<A> actionClass, Implementation<S, A> actionHandlerForType) {
        this.actionClass = actionClass;
        this.actionHandlerForType = actionHandlerForType;
    }

    @Override
    public S reduce(S previousState, Action<?> action) {
        if (actionClass.isInstance(action)) {
            return actionHandlerForType.reduce(previousState, actionClass.cast(action));
        }

        return previousState;
    }
    
    public interface Implementation<S, A> {
        S reduce(S previousState, A action);
    }
}
