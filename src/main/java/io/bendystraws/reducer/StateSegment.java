package io.bendystraws.reducer;

public interface StateSegment<P, S> {
    S extractFrom(P parentState);
    P insert(S newSegment, P parentState);
    P insertIntoNewParent(S newSegment);
}
