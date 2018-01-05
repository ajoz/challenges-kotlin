package io.github.ajoz.sequences

import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.contain
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.WordSpec

class SequencesTest : WordSpec() {
    init {
        "Empty Sequence" should {
            "return an empty sequence after scan transformation" {
                val emptySeq = emptySequence<Int>()
                val transform = { prev: Int, next: Int -> prev + next }

                emptySeq.scan(0, transform).toList() should beEmpty()
            }

            "throw exception when checked for first repeated element" {
                shouldThrow<NoSuchElementException> {
                    val emptySeq = emptySequence<Int>()
                    emptySeq.firstRepeated()
                }
            }
        }

        "One element Sequence: [1]" should {
            "map to [1] when scanned with 0 for a sum transformation" {
                val oneElemSeq = sequenceOf(1)
                val transform = { prev: Int, next: Int -> prev + next }

                val actual = oneElemSeq.scan(0, transform).toList()

                actual should contain(1)
            }

            "throw exception when checked for first repeated element" {
                shouldThrow<NoSuchElementException> {
                    val oneElemSeq = sequenceOf(1)
                    oneElemSeq.firstRepeated()
                }
            }
        }

        "Multi element Sequence: [1, 2, 2, 3]" should {
            "map to [1, 3, 5, 8] when scanned with 0 for a sum transformation" {
                val multiElemSeq = sequenceOf(1, 2, 2, 3)
                val expected = listOf(1, 3, 5, 8)
                val transform = { prev: Int, next: Int -> prev + next }

                val actual = multiElemSeq.scan(0, transform).toList()

                actual.size shouldBe expected.size
                actual shouldBe expected
            }

            "return 2 when checked for fist repeated element" {
                val multiElemSeq = sequenceOf(1, 2, 2, 3)

                multiElemSeq.firstRepeated() shouldBe 2
            }
        }
    }
}