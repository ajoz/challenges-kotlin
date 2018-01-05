package io.github.ajoz.fsm

import io.kotlintest.matchers.contain
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.WordSpec

class StateTest : WordSpec() {
    init {
        "State" should {
            "use shorter form of toString" {
                State(1).toString() shouldBe "State(1)"
                State('A').toString() shouldBe "State(A)"
                State("TestState").toString() shouldBe "State(TestState)"
            }

            "return a StateSet after call to 'or' method with another State" {
                val set = State(1) or State(2)

                set.values.size shouldBe 2
                set.values should contain(State(1))
                set.values should contain(State(2))
            }

            "return a Match after call to 'after' method with a Symbol" {
                val match = State(1) after Symbol('R')
                match.state shouldBe State(1)
                match.symbol shouldBe Symbol('R')
            }
        }
    }
}