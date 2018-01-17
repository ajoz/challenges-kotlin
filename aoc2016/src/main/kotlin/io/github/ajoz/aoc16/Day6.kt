package io.github.ajoz.aoc16

import io.github.ajoz.aoc16.Message.*
import java.io.File

/*
--- Day 6: Signals and Noise ---

Something is jamming your communications with Santa. Fortunately, your signal is
only partially jammed, and protocol in situations like this is to switch to a
simple repetition code to get the message through.

In this model, the same message is sent repeatedly. You've recorded the
repeating message signal (your puzzle input), but the data seems quite
corrupted - almost too badly to recover. Almost.

All you need to do is figure out which character is most frequent for each
position. For example, suppose you had recorded the following messages:

eedadn
drvtee
eandsr
raavrd
atevrs
tsrnev
sdttsa
rasrtv
nssdts
ntnada
svetve
tesnvt
vntsnd
vrdear
dvrsen
enarar

The most common character in the first column is e; in the second, a; in the
third, s, and so on. Combining these characters returns the error-corrected
message, easter.

Given the recording in your puzzle input, what is the error-corrected version
of the message being sent?
 */

typealias Signal = String
typealias Frequencies = List<List<Char>>

sealed class Message {
    object Empty : Message() {
        operator override fun plus(signal: Signal): Message {
            return Noisy(signal
                    .toList()
                    .map { listOf(it) }
            )
        }
    }

    data class Noisy(val frequencies: Frequencies) : Message() {
        operator override fun plus(signal: Signal): Message {
            return Noisy(signal
                    .toList()
                    .zip(frequencies) { a: Char, b: List<Char> ->
                        b + a
                    }
            )
        }
    }

    fun getMessage(removeNoise: (List<Char>) -> Char?) =
            when (this) {
                is Empty -> ""
                is Noisy -> this.frequencies.mapNotNull {
                    removeNoise(it)
                }.joinToString(separator = "")
            }

    operator abstract fun plus(sig: Signal): Message
}

fun highFreqNoiseRemove(freq: List<Char>): Char? =
        freq.groupingBy { it }.eachCount().maxBy { it.value }?.key

fun getDay6Part1(input: List<String>): String =
        input.fold(Empty) { msg: Message, sig: Signal ->
            msg + sig
        }.getMessage(::highFreqNoiseRemove)

/*
--- Part Two ---

Of course, that would be the message - if you hadn't agreed to use a modified
repetition code instead.

In this modified code, the sender instead transmits what looks like random data,
but for each character, the character they actually want to send is slightly
less likely than the others. Even after signal-jamming noise, you can look at
the letter distributions in each column and choose the least common letter to
reconstruct the original message.

In the above example, the least common character in the first column is a; in
the second, d, and so on. Repeating this process for the remaining characters
produces the original message, advent.

Given the recording in your puzzle input and this new decoding methodology,
what is the original message that Santa is trying to send?
 */

fun lowFreqNoiseRemove(freq: List<Char>): Char? =
        freq.groupingBy { it }.eachCount().minBy { it.value }?.key


fun getDay6Part2(input: List<String>): String =
        input.fold(Empty) { msg: Message, sig: Signal ->
            msg + sig
        }.getMessage(::lowFreqNoiseRemove)

val inputDay6 = File("src/main/resources/day6-puzzle-input")

fun main(args: Array<String>) {
    println(getDay6Part1(inputDay6.readLines()))
    println(getDay6Part2(inputDay6.readLines()))
}