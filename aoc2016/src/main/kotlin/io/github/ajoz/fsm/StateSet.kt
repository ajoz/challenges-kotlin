package io.github.ajoz.fsm

/**
 * Contains a set of [State] this allows to construct a small DSL for creating list of possible transition for the FSM.
 */
data class StateSet<T>(val values: Set<State<T>>) {
    /**
     * Creates a [StateSet] from this set and a given [State]. Very useful when chaining more states then two. This
     * method allows us to write:
     *
     * State(1) or State(2) or State(3)
     */
    infix fun or(state: State<T>) = StateSet(values + state)

    /**
     * Creates a cycle [Transitions] for this [StateSet] and a given [Symbol]. Very usefull if we want to express the
     * fact that several States transition to themselves after a given [Symbol]:
     *
     * State(1) or State(2) or State(3) cyclesAfter Symbol('U')
     */
    infix fun <V> cyclesAfter(symbol: Symbol<V>): Transitions<T, V> {
        val transitions = values
                .map { state -> Match(state, symbol) }
                .map { match -> Pair(match, match.state) }
                .toMap()
        return Transitions(transitions)
    }
}