package io.github.ajoz.aoc16

import java.io.File

/**
 * --- Day 4: Security Through Obscurity ---
 *
 * Finally, you come across an information kiosk with a list of rooms. Of
 * course, the list is encrypted and full of decoy data, but the instructions to
 * decode the list are barely hidden nearby. Better remove the decoy data first.
 *
 * Each room consists of an encrypted name (lowercase letters separated by
 * dashes) followed by a dash, a sector ID, and a checksum in square brackets.
 *
 * A room is real (not a decoy) if the checksum is the five most common letters
 * in the encrypted name, in order, with ties broken by alphabetization.
 *
 * For example:
 *
 * 1) aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common letters
 * are a (5), b (3), and then a tie between x, y, and z, which are listed
 * alphabetically.
 *
 * 2) a-b-c-d-e-f-g-h-987[abcde] is a real room because although the letters are
 * all tied (1 of each), the first five are listed alphabetically.
 *
 * 3) not-a-real-room-404[oarel] is a real room.
 * 4) totally-real-room-200[decoy] is not.
 *
 * Of the real rooms from the list above, the sum of their sector IDs is 1514.
 *
 * What is the sum of the sector IDs of the real rooms?
 */

data class Room(val name: String, val id: Int, val checksum: String)

fun Room.isValidRoom(): Boolean {
    return checksum == getChecksum(name)
}

fun getChecksum(roomName: String) =
        roomName.groupingBy { it }
                .eachCount()
                .filterKeys { it != '-' }
                .toList()
                .sortedWith(Comparator { entry1, entry2 ->
                    val diff = entry2.second - entry1.second
                    if (diff != 0) diff
                    else entry1.first.compareTo(entry2.first)
                })
                .map { entry -> entry.first }
                .take(5)
                .joinToString(separator = "")

fun MatchGroupCollection.toRoom(): Room? {
    val roomName = this[1]?.value
    val roomId = this[2]?.value?.toInt()
    val roomCheckSum = this[3]?.value

    return if (roomName != null && roomId != null && roomCheckSum != null) {
        Room(roomName, roomId, roomCheckSum)
    } else null
}

fun getValidRooms(rooms: List<String>): List<Room> {
    val regex = """([a-z\-]+)-([0-9]+)\[([a-z]+)]""".toRegex()
    return rooms
            .mapNotNull { regex.matchEntire(it)?.groups }
            .filter { it.size == 4 }
            .mapNotNull { it.toRoom() }
            .filter { it.isValidRoom() }
}

fun getPart1SumOfSectorIds(rooms: List<String>): Int {
    return getValidRooms(rooms)
            .map { it.id }
            .sum()
}

const val NUM_OF_LETTERS = 26
const val ASCII_A_CODE = 65

fun rotN(string: String, n: Int) = string
        .map {
            if (it.isLetter()) {
                (it.toAlphabet() + n).rem(NUM_OF_LETTERS).fromAlphabet()
            } else it
        }
        .joinToString("")

fun Char.toAlphabet() = this.toUpperCase().toInt() - ASCII_A_CODE
fun Int.fromAlphabet() = (this + ASCII_A_CODE).toChar()

val inputDay4 = File("src/main/resources/day4-puzzle-input")

fun main(args: Array<String>) {
    println(getPart1SumOfSectorIds(inputDay4.readLines()))

    val rooms = getValidRooms(inputDay4.readLines())
            .filter { rotN(it.name, it.id.rem(NUM_OF_LETTERS)).contains("NORTH") }
            .map { it.id }
            .first()

    println(rooms)
}