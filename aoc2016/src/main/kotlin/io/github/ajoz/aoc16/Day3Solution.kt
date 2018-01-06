package io.github.ajoz.aoc16

import java.io.File

/**
 * Day 3: Squares With Three Sides
 *
 * -------------------- Part 1 -----------------------
 *
 * Now that you can think clearly, you move deeper into the labyrinth of hallways and office furniture that makes up
 * this part of Easter Bunny HQ. This must be a graphic design department; the walls are covered in specifications for
 * triangles.
 *
 * Or are they?
 *
 * The design document gives the side lengths of each triangle it describes, but... 5 10 25? Some of these aren't
 * triangles. You can't help but mark the impossible ones.
 *
 * In a valid triangle, the sum of any two sides must be larger than the remaining side. For example, the "triangle"
 * given above is impossible, because 5 + 10 is not larger than 25.
 *
 * In your puzzle input, how many of the listed triangles are possible?
 *
 * https://adventofcode.com/2016/day/3
 */

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

fun getPart1ValidTrianglesCount(rows: List<String>) = rows
        .map {
            it.split(delimiters = " ")
                    .filter(String::isNotBlank)
        }
        .map(::toSpecification)
        .filter(Specification?::isTriangle)
        .count()

/**
 * -------------------- Part 2 -----------------------
 *
 * Now that you've helpfully marked up their design documents, it occurs to you that triangles are specified in groups
 * of three vertically. Each set of three numbers in a column specifies a triangle. Rows are unrelated.
 *
 * For example, given the following specification, numbers with the same hundreds digit would be part of the same
 * triangle:
 *
 * 101 301 501
 * 102 302 502
 * 103 303 503
 * 201 401 601
 * 202 402 602
 * 203 403 603
 *
 * In your puzzle input, and instead reading by columns, how many of the listed triangles are possible?
 *
 * https://adventofcode.com/2016/day/3
 */

fun getPart2ValidTrianglesCount(data: String): Int {

    // unfortunately functions are not curried by default in Kotlin :(
    // I need to the possibility to partially apply the function
    fun getColumn(columns: List<String>): (Int) -> List<String> = { position ->
        columns.drop(position).windowed(1, 3).flatten()
    }

    fun getTriangleCount(columns: List<String>) =
            columns
                    .windowed(3, 3)
                    .map { toSpecification(it) }
                    .filter { it.isTriangle }
                    .count()

    val columns = data.split(delimiters = " ")
            .map { it.trim() }
            .filter(String::isNotBlank)

    return listOf(0, 1, 2).map(getColumn(columns)).map { getTriangleCount(it) }.sum()
}

val input = File("src/main/resources/day3/puzzle-input")

fun main(args: Array<String>) {
    println(getPart2ValidTrianglesCount(input.readText()))
    println(getPart1ValidTrianglesCount(input.readLines()))
}

