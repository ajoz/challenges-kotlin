package io.github.ajoz.fsm

/**
 * Describes available transitions between a State Symbol pair and a State.
 */
data class Transitions<T, V>(val values: Map<Match<T, V>, State<T>>) {
    companion object {

        /**
         * Creates a transition function from the given array of transitions. Be aware that this transition function
         * doesn't know what to do in case of a missing State Symbol pair. Here we just simply explode :P
         */
        fun <T, V> transitions(vararg transitions: Transitions<T, V>): (State<T>, Symbol<V>) -> State<T> {
            val t = transitions.fold(emptyMap<Match<T, V>, State<T>>()) {
                map, trans ->
                map + trans.values
            }
            return fun(st: State<T>, sy: Symbol<V>): State<T> {
                val state = t[Match.matchOf(st, sy)]
                return state!! //we want this implementation to die if anything bad happens :)
            }
        }
    }
}