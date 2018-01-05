package io.github.ajoz.fsm

/**
 * Describes a single state that the FSM can be in. It's exposing methods for creating a small DSL for defining all
 * transitions the FSM should handle. The DSL allows to describe the transitions in the form of sentences like:
 *
 * "State(1) or State(2) after Symbol('F') transitionsTo State(3)"
 *
 * As you see its very easy to understand what will happen for the given transition.
 */
data class State<T>(val value: T) {
    /**
     * Although data classes generate toString method, the generated one also uses the name of the property, for the
     * ease of reading this toString implementation is using a shorter notation.
     */
    override fun toString() = "State($value)"

    /**
     * Creates a [StateSet] from this [State] and another given [State]. This [StateSet] can be used to create even
     * bigger set of states with consecutive calls to [StateSet.or].
     */
    infix fun or(other: State<T>) = StateSet(setOf(this, other))

    /**
     * Creates a [Match] from this [State] and the given [Symbol]. A single [Match] describes an input for the
     * transition function: (State, Symbol) -> State used by the FSM to move to the next state.
     */
    infix fun <V> after(symbol: Symbol<V>) = Match(this, symbol)
}