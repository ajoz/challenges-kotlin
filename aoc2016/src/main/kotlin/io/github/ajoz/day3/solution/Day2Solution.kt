package io.github.ajoz.day3.solution

import java.io.File

data class Specification(val a: Int, val b: Int, val c: Int)

val Specification?.isTriangle: Boolean
    get() = when (this) {
        null -> false
        else -> (a + b > c) && (a + c > b) && (b + c > a)
    }

fun toSpecification(list: List<String>): Specification? = when (list.size) {
    3 -> Specification(list[0].toInt(), list[1].toInt(), list[2].toInt())
    else -> null
}

val input = File("src/main/resources/day3/puzzle-input")

fun main(args: Array<String>) {
    val count = input
            .readLines()
            .map {
                it.split(delimiters = " ")
                        .filter(String::isNotBlank)
            }
            .map(::toSpecification)
            .filter(Specification?::isTriangle)
            .count()
    println(count)
}