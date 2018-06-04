package io.bendystraws.reducer;

import io.bendystraws.action.Action;

import java.util.HashMap;

public class ActionMap<S> extends HashMap<Class<? extends Action<?>>, ActionHandler<S, ? extends Action<?>>> {}
