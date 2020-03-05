package io.bendystraws.reducer;

import io.bendystraws.action.Action;

/**
 * A {@link Reducer}that has a mapping of {@link ActionHandler}s to
 * {@link Action} classes contained in an {@link ActionMap}.  The
 * ActionHandler which corresponds to a given Action generates the
 * new state based on the old and returns it.  If the Action is null,
 * the default state given when this Reducer is constructed is returned.
 * If the Action is non-null, but none of this Reducer's ActionHandlers
 * apply to the it, the state which was passed in is returned unchanged.
 *
 * <p>
 *      This class's intended use is to handle actions for one part of
 *      a larger state tree in conjunction with {@link StateSegmentReducer} and
 *      {@link CombinedReducers}, but it can also be used in simple applications
 *      with a small enough state that it does not require a tree structure
 * </p>
 *
 * @param <S> the type of the state object handled by this {@link Reducer}
 */
public class LeafReducer<S> implements Reducer<S> {
    private ActionMap<S> actionHandlers;
    private S defaultState;

    /**
     * @param actionHandlers the {@link ActionHandler}s this {@link Reducer} uses to update the state
     * @param defaultState the state to return in the case of a null {@link Action}
     */
    public LeafReducer(ActionMap<S> actionHandlers, S defaultState) {
        this.actionHandlers = actionHandlers;
        this.defaultState = defaultState;
    }

    /**
     * Convenience method for constructing a {@link LeafReducer}
     *
     * @param actionHandlers the {@link ActionHandler}s this {@link Reducer} uses to update the state
     * @param defaultState the state to return in the case of a null {@link Action}
     * @param <S> the type of the state object handled by this {@link Reducer}
     * @return the new {@link LeafReducer}
     */
    public static <S> Reducer<S> reducer(ActionMap<S> actionHandlers, S defaultState) {
        return new LeafReducer<>(actionHandlers, defaultState);
    }

    /**
     * The {@link ActionHandler} which corresponds to a given {@link Action} generates the
     * new state based on the old and returns it.  If the Action is null,
     * the default state this {@link Reducer} was constructed with is returned.
     * If the Action is non-null, but none of this Reducer's ActionHandlers
     * apply to the it, the state which was passed in is returned unchanged.
     *
     * @param previousState the previous state
     * @param action the action which is causing an update to occur
     * @return the new state, or the default state for a null {@link Action}, or
     * previousState if none of this {@link LeafReducer}'s {@link ActionHandler}s
     * handle the given Action type
     */
    @Override
    public S reduce(S previousState, Action<?> action) {
        if (action == null) { return defaultState; }

        ActionHandler<S, ? extends Action<?>> handler = actionHandlers.get(action.getClass());

        if (handler == null) { return previousState; }

        return handler.reduce(previousState, action);
    }
}
