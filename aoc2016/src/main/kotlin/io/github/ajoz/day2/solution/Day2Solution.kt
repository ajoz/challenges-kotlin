package io.github.ajoz.day2.solution

import io.github.ajoz.fsm.FSM
import io.github.ajoz.fsm.State
import io.github.ajoz.fsm.Symbol
import io.github.ajoz.fsm.Transitions.Companion.transitions
import io.github.ajoz.sequences.scan
import io.github.ajoz.strings.head
import io.github.ajoz.strings.tail

/**
 * We can see the solution to this puzzle in terms of a deterministic FSM (finite state machine). An input alphabet of
 * a FSM consists of a set of symbols, states change to next states due to a transition function. Each function can be
 * easily expressed just as a map of some value to another value. We can define FSM transition function in terms of a
 * mapping (State, Symbol) -> State. This can be expressed as a Map<Pair<State, Symbol>, State>. We can now prepare
 * a transition function with a DSL made just for this puzzle.
 *
 * Our set of possible states is subset of Int: 1, 2, 3, 4, 5, 6, 7, 8, 9
 * Our set of possible symbols is subset of Char: U, R, L, D
 *
 * We want to have a DSL expressive enough to allow writing like:
 *
 * 1 or 2 or 3 cycles after U
 *
 * In the fsm package you can find a simple implementation of such DSL.
 */

val Char.symbol: Symbol<Char>
    get() = Symbol(this)

tailrec fun acceptIntChar(fsm: FSM<Int, Char>, instruction: String): FSM<Int, Char> = when {
    instruction.isEmpty() -> fsm
    else -> acceptIntChar(fsm.accept(instruction.head.symbol), instruction.tail)
}

val day2part1Transition = transitions(
        (State(1) or State(2) or State(3)) cyclesAfter Symbol('U'),
        (State(1) or State(4) or State(7)) cyclesAfter Symbol('L'),
        (State(3) or State(6) or State(9)) cyclesAfter Symbol('R'),
        (State(7) or State(8) or State(9)) cyclesAfter Symbol('D'),
        State(1) after Symbol('R') transitionsTo State(2),
        State(1) after Symbol('D') transitionsTo State(4),
        State(2) after Symbol('L') transitionsTo State(1),
        State(2) after Symbol('R') transitionsTo State(3),
        State(2) after Symbol('D') transitionsTo State(5),
        State(3) after Symbol('L') transitionsTo State(2),
        State(3) after Symbol('D') transitionsTo State(6),
        State(4) after Symbol('U') transitionsTo State(1),
        State(4) after Symbol('R') transitionsTo State(5),
        State(4) after Symbol('D') transitionsTo State(7),
        State(5) after Symbol('U') transitionsTo State(2),
        State(5) after Symbol('L') transitionsTo State(4),
        State(5) after Symbol('R') transitionsTo State(6),
        State(5) after Symbol('D') transitionsTo State(8),
        State(6) after Symbol('U') transitionsTo State(3),
        State(6) after Symbol('L') transitionsTo State(5),
        State(6) after Symbol('D') transitionsTo State(9),
        State(7) after Symbol('U') transitionsTo State(4),
        State(7) after Symbol('R') transitionsTo State(8),
        State(8) after Symbol('U') transitionsTo State(5),
        State(8) after Symbol('L') transitionsTo State(7),
        State(8) after Symbol('R') transitionsTo State(9),
        State(9) after Symbol('U') transitionsTo State(6),
        State(9) after Symbol('L') transitionsTo State(8)
)

fun getPart1BathroomAccessCode(instructions: String): Int {
    val fsm = FSM(State(5), day2part1Transition)
    return instructions.splitToSequence(delimiters = '\n')
            .scan(fsm, ::acceptIntChar)
            .map { fsm -> fsm.state.value }
            .fold("") {
                str, value ->
                str + value
            }.toInt()
}

val day2part2Transition = transitions(
        State('1') or State('2') or State('5') or State('A') or State('D') cyclesAfter Symbol('L'),
        State('1') or State('4') or State('9') or State('C') or State('D') cyclesAfter Symbol('R'),
        State('5') or State('2') or State('1') or State('4') or State('9') cyclesAfter Symbol('U'),
        State('5') or State('A') or State('D') or State('C') or State('9') cyclesAfter Symbol('D'),
        State('1') after Symbol('D') transitionsTo State('3'),
        State('2') after Symbol('D') transitionsTo State('6'),
        State('4') after Symbol('D') transitionsTo State('8'),
        State('2') after Symbol('R') transitionsTo State('3'),
        State('4') after Symbol('L') transitionsTo State('3'),
        State('5') after Symbol('R') transitionsTo State('6'),
        State('9') after Symbol('L') transitionsTo State('8'),
        State('A') after Symbol('U') transitionsTo State('6'),
        State('A') after Symbol('R') transitionsTo State('B'),
        State('C') after Symbol('U') transitionsTo State('8'),
        State('C') after Symbol('L') transitionsTo State('B'),
        State('D') after Symbol('U') transitionsTo State('B'),
        State('3') after Symbol('U') transitionsTo State('1'),
        State('3') after Symbol('L') transitionsTo State('2'),
        State('3') after Symbol('R') transitionsTo State('4'),
        State('3') after Symbol('D') transitionsTo State('7'),
        State('6') after Symbol('U') transitionsTo State('2'),
        State('6') after Symbol('L') transitionsTo State('5'),
        State('6') after Symbol('R') transitionsTo State('7'),
        State('6') after Symbol('D') transitionsTo State('A'),
        State('7') after Symbol('U') transitionsTo State('3'),
        State('7') after Symbol('L') transitionsTo State('6'),
        State('7') after Symbol('R') transitionsTo State('8'),
        State('7') after Symbol('D') transitionsTo State('B'),
        State('8') after Symbol('U') transitionsTo State('4'),
        State('8') after Symbol('L') transitionsTo State('7'),
        State('8') after Symbol('R') transitionsTo State('9'),
        State('8') after Symbol('D') transitionsTo State('C'),
        State('B') after Symbol('U') transitionsTo State('7'),
        State('B') after Symbol('L') transitionsTo State('A'),
        State('B') after Symbol('R') transitionsTo State('C'),
        State('B') after Symbol('D') transitionsTo State('D')
)

tailrec fun acceptCharChar(fsm: FSM<Char, Char>, instruction: String): FSM<Char, Char> = when {
    instruction.isEmpty() -> fsm
    else -> acceptCharChar(fsm.accept(instruction.head.symbol), instruction.tail)
}

fun getPart2BathroomAccessCode(instructions: String): String {
    val fsm = FSM(State('5'), day2part2Transition)
    return instructions.splitToSequence(delimiters = '\n')
            .scan(fsm, ::acceptCharChar)
            .map { fsm -> fsm.state.value }
            .fold("") {
                str, value ->
                str + value
            }
}