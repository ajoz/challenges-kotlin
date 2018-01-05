package io.github.ajoz.aoc16

import io.github.ajoz.sequences.firstRepeated
import io.github.ajoz.sequences.scan

/**
 * Day 1: No Time for a Taxicab
 *
 * -------------------- Part 1 -----------------------
 *
 * You're airdropped near Easter Bunny Headquarters in a city somewhere. "Near", unfortunately, is as close as you can get
 * - the instructions on the Easter Bunny Recruiting Document the Elves intercepted start here, and nobody had time to work
 * them out further.
 *
 * The Document indicates that you should start at the given coordinates (where you just landed) and face North. Then,
 * follow the provided sequence: either turn left (L) or right (R) 90 degrees, then walk forward the given number of
 * blocks, ending at a new intersection.
 *
 * There's no time to follow such ridiculous instructions on foot, though, so you take a moment and work out the
 * destination. Given that you can only walk on the street grid of the city, how far is the shortest path to the
 * destination?
 *
 * http://adventofcode.com/2016/day/1
 */

/**
 * Describes all possible directions that we can take in a taxi cab geometry.
 */
private enum class Direction {
    NORTH,
    EAST,
    WEST,
    SOUTH
}

/**
 * Describes a single Instruction in terms of direction instead of terms of Left or Right turn.
 */
private data class Instruction(val direction: Direction, val distance: Int)

/**
 * Allows to generate a next [Instruction] from the given instruction string representation. In the puzzle it is stated
 * that we can have only two types of instructions: 'R' or 'L'. There is no theoretical limit to the size of the
 * instruction string or if the string instructions can be incorrect (wrong letter or more letters, no number of blocks,
 * negative number of blocks etc).
 *
 * After looking at the input data given, I assumed:
 * - instruction has only one letter {'R', 'L'}
 * - instruction has only a positive number of blocks
 * - number of blocks will never reach maximum of integer ;)
 *
 * To calculate next [Instruction] we need previous [Direction] and the single string instruction. For example let's say
 * that the current [Direction] is [Direction.EAST] and string instruction is 'L', then if we are pointing east and we
 * look on the left our next direction will be [Direction.NORTH].
 *
 * I've implemented it as an extension function to the [Instruction] type to allow shorter notation in lambdas.
 */
private fun Instruction.next(instruction: String): Instruction {
    val turn = instruction.substring(0, 1)
    val numOfBlocks = instruction.substring(1).toInt()

    return when (turn) {
        "R" -> when (this.direction) {
            Direction.NORTH -> Instruction(Direction.EAST, numOfBlocks)
            Direction.EAST -> Instruction(Direction.SOUTH, numOfBlocks)
            Direction.WEST -> Instruction(Direction.NORTH, numOfBlocks)
            Direction.SOUTH -> Instruction(Direction.WEST, numOfBlocks)
        }
        "L" -> when (this.direction) {
            Direction.NORTH -> Instruction(Direction.WEST, numOfBlocks)
            Direction.EAST -> Instruction(Direction.NORTH, numOfBlocks)
            Direction.WEST -> Instruction(Direction.SOUTH, numOfBlocks)
            Direction.SOUTH -> Instruction(Direction.EAST, numOfBlocks)
        }
        else -> throw IllegalArgumentException("Unknown direction for instruction: $instruction")
    }
}

/**
 * Class represents calculated coordinates of each step of the route to Bunny HQ.
 */
private data class Coordinates(val x: Int, val y: Int) {
    /**
     * Calculates the L1 distance (other names include: l1 norm, snake distance, city block distance, Manhattan
     * distance, Manhattan length, rectilinear distance).
     *
     * @param to [Coordinates] to which the L1 distance will be calculated.
     */
    fun getL1distance(to: Coordinates) = Math.abs(this.x - to.x) + Math.abs(this.y - to.y)
}

/**
 * Allows to generate coordinates of the next step of the route. As we can go only in any of the four directions: North,
 * East, West or South then Coordinates of next step will either differ in the x or y -axis to the previous one.
 *
 * I used a copy method that helps to create a new object based on the given one (also change only set of its
 * properties)
 */
private fun Coordinates.next(direction: Direction, distance: Int) = when (direction) {
    Direction.NORTH -> this.copy(y = this.y + distance)
    Direction.EAST -> this.copy(x = this.x + distance)
    Direction.WEST -> this.copy(x = this.x - distance)
    Direction.SOUTH -> this.copy(y = this.y - distance)
}

/**
 * Allows to generate coordinates of the next step of the route.
 */
private fun Coordinates.next(instruction: Instruction) = next(instruction.direction, instruction.distance)

/**
 * Calculates the shortest path from the start [Coordinates] (0, 0) to the [Coordinates] calculated by processing the
 * given instructions string.
 *
 * Algorithm is simple:
 * 1. split the instruction string into a [Sequence] of [String] (from: "L2, L3" we get: "L2", " L3")
 * 2. trim the white chars from the [Sequence] of [String] we got in previous step
 * 3. each instruction can be built only when we know what was the previous instruction, so we [scan] the whole
 * [Sequence] of [String]. Method [scan] for a Sequence<T> takes an initial value of type R and a lambda (R, T) -> R to
 * process all the elements of the sequence. As the function (R, T) -> R expects two arguments, for the first element in
 * the Sequence<T> the specified initial value is used. This way in our case we can easily calculate each [Instruction].
 * 4. Change [Instruction] to [Coordinates]
 * 5. Pick last [Coordinates] from the [Sequence]
 */
fun getShortestPathLengthToDestination(instructions: String): Int {
    val startInstruction = Instruction(Direction.NORTH, 0) //after we land in the city we are pointing North

    val startCoords = Coordinates(0, 0)
    val stopCoords = instructions
            .splitToSequence(delimiters = ",", ignoreCase = true)
            .map(String::trim)
            .scan(startInstruction, Instruction::next)
            .scan(startCoords, Coordinates::next)
            .last()

    return startCoords.getL1distance(stopCoords)
}

/**
 * -------------------- Part 2 -----------------------
 *
 * Then, you notice the instructions continue on the back of the Recruiting Document. Easter Bunny HQ is actually at the
 * first location you visit twice. For example, if your instructions are R8, R4, R4, R8, the first location you visit
 * twice is 4 blocks away, due East.
 *
 * http://adventofcode.com/2016/day/1
 */

/**
 * Calculates a [List] of [Coordinates] for a given [Direction] and a distance. It's working through a infinite
 * [Sequence] of value 1 (this is the step), it takes only first few values (the amount of values taken is equal to the
 * distance), then it just calculates [Coordinates] on the path in the given [Direction].
 *
 * Before I implement a nice flatScan method, we need to return a [List] because
 */
private fun Coordinates.path(direction: Direction, distance: Int) =
        generateSequence { 1 }
                .take(distance)
                .scan(this) { coordinates, value -> coordinates.next(direction, value) }
                .toList()

/**
 * Calculates a [List] of [Coordinates] for a given [Instruction].
 */
private fun Coordinates.path(instruction: Instruction) = path(instruction.direction, instruction.distance)

/**
 * Calculates the shortest path from the start [Coordinates] (0, 0) to the first repeating [Coordinates] calculated by
 * processing the given instructions string.
 *
 * Algorithm is simple:
 * 1. split the instruction string into a [Sequence] of [String] (from: "L2, L3" we get: "L2", " L3")
 * 2. trim the white chars from the [Sequence] of [String] we got in previous step
 * 3. each instruction can be built only when we know what was the previous instruction, so we [scan] the whole
 * [Sequence] of [String]. Method [scan] for a Sequence<T> takes an initial value of type R and a lambda (R, T) -> R to
 * process all the elements of the sequence. As the function (R, T) -> R expects two arguments, for the first element in
 * the Sequence<T> the specified initial value is used. This way in our case we can easily calculate each [Instruction].
 * 4. between each previous and next [Coordinates] a [List] of [Coordinates] is calculated (a path). As we are working
 * after [Int] values for coordinates, the calculated path will have a difference of 1 between two coordinates that are
 * next of each other (in terms of x or y value)
 * 5. we need to flatten Sequence<List<Coordinates>> to Sequence<Coordinates>
 * 6. we take the fist repeated coordinates
 */
fun getShortestPathLengthToFirstRepeatedDestination(instructions: String): Int {
    val startInstruction = Instruction(Direction.NORTH, 0) //after we land in the city we are pointing North

    val startCoords = Coordinates(0, 0)
    val partialPath = instructions
            .splitToSequence(delimiters = ",", ignoreCase = true)
            .map(String::trim)
            .scan(startInstruction, Instruction::next)
            .scan(listOf(startCoords)) { list, instr -> list.last().path(instr) } //flatScan??
            .flatten()

    val fullPath = sequenceOf(startCoords) + partialPath

    val stopCoords = fullPath.firstRepeated()

    return startCoords.getL1distance(stopCoords)
}