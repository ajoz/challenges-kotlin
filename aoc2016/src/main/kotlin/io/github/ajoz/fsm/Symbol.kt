package io.github.ajoz.fsm

/**
 * Describes a single symbol from the alphabet used by the state machine.
 */
data class Symbol<T>(val value: T) {
    /**
     * Although data classes generate toString method, the generated one also uses the name of the property, for the
     * ease of reading this toString implementation is using a shorter notation.
     */
    override fun toString() = "Symbol($value)"

    /**
     * Creates a [SymbolSet] from this [Symbol] and the given one. This [SymbolSet] can be used to build larger
     * transitions for the FSM. It's especially useful in cases:
     * - a state has the same transition for several symbols:
     * State(1) after (Symbol('U') or Symbol('R')) transitionsTo State(2)
     * - a state has a cycle (points to itself) for several symbols:
     * State(1) cyclesAfter (Symbol('U') or Symbol('D'))
     * - a set of states has the same transition for several symbols:
     * (State(1) or State(2)) after (Symbol('L') or Symbol('R')) transitionsTo State(3)
     */
    infix fun or(other: Symbol<T>) = SymbolSet(setOf(this, other))
}