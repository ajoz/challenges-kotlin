package io.github.ajoz.fsm

/**
 * Simple example of a immutable finite state machine. There are few issues with this naive implementation:
 * - we do not guarantee that the set of possible states is finite due to State<T>
 * - we do not check if the set of possible state is not empty
 * - we do not guarantee that the set of possible symbols in the FSM alphabet is finite due to Symbol<V>
 * - we do not check if the set of possible symbols is not empty
 * - we do not explicitly define a set of final states
 *
 * Maybe "few" is a misuse of the word :P Nonetheless this trivial implementation shows an immutable approach
 */
class FSM<T, V>(val state: State<T>, transition: (State<T>, Symbol<V>) -> State<T>) {
    val transition = transition

    /**
     * Returns instance of the machine set to the next state after using the transition function. Doesn't handle any
     * exceptional situations.
     */
    fun accept(symbol: Symbol<V>): FSM<T, V> {
        val nextState = transition(state, symbol)
        return FSM(nextState, transition)
    }
}