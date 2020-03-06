package io.bendystraws.reducer;

import io.bendystraws.action.Action;

/**
 * A {@link Reducer} which checks if a given {@link Action} applies to itself
 * and returns the unchanged state if it does not, and a new state based on
 * the specified {@link Implementation} if it does.  Also capable of reporting
 * what type of {@link Action} it's meant to handle.  This class ensures the
 * {@link Reducer} it calls on will always receive the {@link Action} type it's
 * expecting, preventing the need for manual casting and type-checking.
 *
 * See also {@link ActionMap}
 *
 * @param <S> The type of the state object that this handler deals with
 * @param <A> The {@link Action} type this handler deals with
 */
public class ActionHandler<S, A extends Action<?>> implements Reducer<S> {
    private Class<A> actionClass;
    private Implementation<S, A> actionHandlerForType;

    /**
     * @param actionClass the type of action this {@link ActionHandler} acts on
     * @param actionHandlerForType defines how the state is updated when the reduced state and {@link Action} apply to this {@link ActionHandler}
     */
    public ActionHandler(Class<A> actionClass, Implementation<S, A> actionHandlerForType) {
        this.actionClass = actionClass;
        this.actionHandlerForType = actionHandlerForType;
    }

    /**
     * Convenience method for creating {@link ActionHandler}s
     * @param actionClass the type of action this {@link ActionHandler} acts on
     * @param actionHandlerForType defines how the state is updated when the reduced state and {@link Action} apply to this {@link ActionHandler}
     * @param <S> The type of the state object that this handler deals with
     * @param <A> The {@link Action} type this handler deals with
     * @return the new {@link ActionHandler}
     */
    public static <S, A extends Action<?>> ActionHandler<S, A> handlerFor(Class<A> actionClass, Implementation<S, A> actionHandlerForType) {
        return new ActionHandler<>(actionClass, actionHandlerForType);
    }

    /**
     * Updates the given state if the action applies, otherwise returns the same state
     *
     * @param previousState the previous state
     * @param action the {@link Action} that is causing the state to be updated
     * @return the updated state, or the same state if the action does not apply to this {@link ActionHandler}
     */
    @Override
    public S reduce(S previousState, Action<?> action) {
        if (actionClass.isInstance(action)) {
            return actionHandlerForType.reduce(previousState, actionClass.cast(action));
        }

        return previousState;
    }

    /**
     * @return the {@link Action} type handled by this handler
     */
    public Class<A> getActionClass() {
        return actionClass;
    }

    /**
     * Defines how the state is updated when an {@link Action} applies to an {@link ActionHandler} associated
     * with this Implementation
     *
     * @param <S> The type of the state object that this {@link Implementation} deals with
     * @param <A> The {@link Action} type this {@link Implementation} deals with
     */
    public interface Implementation<S, A> {
        S reduce(S previousState, A action);
    }
}
