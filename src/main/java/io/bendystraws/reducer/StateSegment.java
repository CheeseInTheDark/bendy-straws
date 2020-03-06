package io.bendystraws.reducer;

/**
 * Informs a {@link StateSegmentReducer} how to extract and reinsert
 * a piece of a larger state object
 *
 * @param <P> The type of the parent state
 * @param <S> The type of the child state used in this StateSegment
 */
public interface StateSegment<P, S> {

    /**
     * Returns a child of a parent state object
     *
     * @param parentState the parent state object
     * @return the child state
     */
    S extractFrom(P parentState);

    /**
     * Defines how a state object is reincorporated into its parent
     *
     * @param newSegment the state object to reincorporate
     * @param parentState the parent state object
     * @return the parent state with the new child state inserted
     */
    P insert(S newSegment, P parentState);

    /**
     * Defines how a parent state is created and how its child state
     * object is incorporated, given the parent state is initially null
     *
     * @param newSegment the state object to incorporate in the new parent
     * @return a new parent state which includes the child state object
     */
    P insertIntoNewParent(S newSegment);
}
