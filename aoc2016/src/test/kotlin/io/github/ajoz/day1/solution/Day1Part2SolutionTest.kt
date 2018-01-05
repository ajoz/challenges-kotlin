package io.github.ajoz.day1.solution

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.WordSpec

class Day1Part2SolutionTest : WordSpec() {
    init {
        "The shortest path to first repeated destination (real Bunny HQ)" should {
            "be 0 for a short all RIGHT (R) round route from start to finish" {
                getShortestPathLengthToFirstRepeatedDestination("R2, R2, R2, R2") shouldBe 0
            }

            "be 0 for a short all LEFT (L) round route from start to finish" {
                getShortestPathLengthToFirstRepeatedDestination("L2, L2, L2, L2") shouldBe 0
            }

            "be 0 for a long all RIGHT (R) round route from start to finish" {
                getShortestPathLengthToFirstRepeatedDestination("R10000, R10000, R10000, R10000") shouldBe 0
            }

            "be 0 for a long all LEFT (L) round route from start to finish" {
                getShortestPathLengthToFirstRepeatedDestination("L10000, L10000, L10000, L10000") shouldBe 0
            }

            "be 0 for a short route from start to finish" {
                getShortestPathLengthToFirstRepeatedDestination("L3, L2, R1, L1, L3, L1, R1, L2") shouldBe 0
            }

            "be 4 for AOC'16 Example: R8, R4, R4, R8" {
                getShortestPathLengthToFirstRepeatedDestination("R8, R4, R4, R8") shouldBe 4
            }

            "be 142 for a AOC'16 contest puzzle input #1" {
                getShortestPathLengthToFirstRepeatedDestination("""R3, L5, R2, L1, L2, R5, L2, R2, L2, L2, L1, R2, L2,
                R4, R4, R1,
                L2, L3, R3, L1, R2, L2, L4, R4, R5, L3, R3, L3, L3, R4, R5, L3, R3, L5, L1, L2, R2, L1, R3, R1, L1,
                R187, L1, R2, R47, L5, L1, L2, R4, R3, L3, R3, R4, R1, R3, L1, L4, L1, R2, L1, R4, R5, L1, R77, L5, L4,
                R3, L2, R4, R5, R5, L2, L2, R2, R5, L2, R194, R5, L2, R4, L5, L4, L2, R5, L3, L2, L5, R5, R2, L3, R3,
                R1, L4, R2, L1, R5, L1, R5, L1, L1, R3, L1, R5, R2, R5, R5, L4, L5, L5, L5, R3, L2, L5, L4, R3, R1, R1,
                R4, L2, L4, R5, R5, R4, L2, L2, R5, R5, L5, L2, R4, R4, L4, R1, L3, R1, L1, L1, L1, L4, R5, R4, L4, L4,
                R5, R3, L2, L2, R3, R1, R4, L3, R1, L4, R3, L3, L2, R2, R2, R2, L1, L4, R3, R2, R2, L3, R2, L3, L2, R4,
                L2, R3, L4, R5, R4, R1, R5, R3""") shouldBe 142
            }

            "be 130 for AOC'16 contest puzzle input #2" {
                getShortestPathLengthToFirstRepeatedDestination("""L2, L3, L3, L4, R1, R2, L3, R3, R3, L1, L3, R2, R3,
                L3, R4, R3,
                R3, L1, L4, R4, L2, R5, R1, L5, R1, R3, L5, R2, L2, R2, R1, L1, L3, L3, R4, R5, R4, L1, L189, L2, R2,
                L5, R5, R45, L3, R4, R77, L1, R1, R194, R2, L5, L3, L2, L1, R5, L3, L3, L5, L5, L5, R2, L1, L2, L3, R2,
                R5, R4, L2, R3, R5, L2, L2, R3, L3, L2, L1, L3, R5, R4, R3, R2, L1, R2, L5, R4, L5, L4, R4, L2, R5, L3,
                L2, R4, L1, L2, R2, R3, L2, L5, R1, R1, R3, R4, R1, R2, R4, R5, L3, L5, L3, L3, R5, R4, R1, L3, R1, L3,
                R3, R3, R3, L1, R3, R4, L5, L3, L1, L5, L4, R4, R1, L4, R3, R3, R5, R4, R3, R3, L1, L2, R1, L4, L4, L3,
                L4, L3, L5, R2, R4, L2""") shouldBe 130
            }
        }
    }
}

