package io.bendystraws.reducer;

import io.bendystraws.action.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores {@link ActionHandler}s and retrieves them by class.  Only one {@link ActionHandler}
 * is allowed per {@link Action} type.
 *
 * @param <S> the type of the state object that the {@link ActionHandler}s in this ActionMap return
 */
public class ActionMap<S> {

    private Map<Class<? extends Action<?>>, ActionHandler<S, ? extends Action<?>>> underlyingMap;

    /**
     * @param handlers a list of the {@link ActionHandler}s to use
     */
    public ActionMap(List<ActionHandler<S, ? extends Action<?>>> handlers) {
        underlyingMap = new HashMap<>();

        for(ActionHandler<S, ? extends Action<?>> handler: handlers) {
            underlyingMap.put(handler.getActionClass(), handler);
        }
    }

    /**
     * Convenience method for creating an {@link ActionMap}
     *
     * @param handlers the {@link ActionHandler}s to use
     * @param <S> the type of the state object that the {@link ActionHandler}s in this ActionMap return
     * @return the new {@link ActionMap}
     */
    public static <S> ActionMap<S> handlers(List<ActionHandler<S, ? extends Action<?>>> handlers) {
        return new ActionMap<>(handlers);
    }

    /**
     * @param actionClass the {@link Action} type
     * @return the {@link ActionHandler} corresponding to the given {@link Action}, or null if one does not exist
     */
    public ActionHandler<S, ? extends Action> get(Class<? extends Action> actionClass) {
        return underlyingMap.get(actionClass);
    }
}
