package io.bendystraws.reducer;

import io.bendystraws.action.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionMap<S> {

    private Map<Class<? extends Action<?>>, ActionHandler<S, ? extends Action<?>>> underlyingMap;

    public ActionMap(List<ActionHandler<S, ? extends Action<?>>> handlers) {
        underlyingMap = new HashMap<>();

        for(ActionHandler<S, ? extends Action<?>> handler: handlers) {
            underlyingMap.put(handler.getActionClass(), handler);
        }
    }

    public ActionHandler<S, ? extends Action> get(Class<? extends Action> actionClass) {
        return underlyingMap.get(actionClass);
    }
}
