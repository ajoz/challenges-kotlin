package io.github.ajoz.fsm

/**
 * Describes a match of a State and a Symbol used by the transition function in the FSM. I didn't want to use Pair type
 * for this so I could lower the amount of extension methods on stdlib types.
 */
data class Match<T, V>(val state: State<T>, val symbol: Symbol<V>) {
    /**
     * Although data classes generate toString method, the generated one also uses the name of the property, for the
     * ease of reading this toString implementation is using a shorter notation.
     */
    override fun toString() = "Match(state=${state.value}, symbol=${symbol.value})"

    /**
     * Creates a single transition from a [State] after a [Symbol] to a [State]
     */
    infix fun transitionsTo(state: State<T>) = Transitions(mapOf(this to state))

    companion object {
        /**
         * Creates a match for given types.
         */
        fun <T, V> matchOf(state: T, symbol: V) = Match(State(state), Symbol(symbol))

        /**
         * Creates a match for given State and Symbol
         */
        fun <T, V> matchOf(state: State<T>, symbol: Symbol<V>) = Match(state, symbol)
    }
}