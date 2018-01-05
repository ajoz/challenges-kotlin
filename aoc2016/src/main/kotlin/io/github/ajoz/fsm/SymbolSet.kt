package io.github.ajoz.fsm

/**
 * Contains a set of [Symbol] this allows to construct a small DSL for creating list of possible transition for the FSM.
 */
data class SymbolSet<T>(val values: Set<Symbol<T>>) {
    /**
     * Creates a [SymbolSet] from this set and a given [Symbol]. Very useful when chaining more symbols then two. This
     * method allows us to write:
     *
     * Symbol('U') or Symbol('D')
     *
     * Thanks to such notation we can express that a transition from one State to another State can happen after several
     * different Symbols.
     */
    infix fun or(symbol: Symbol<T>) = SymbolSet(values + symbol)
}