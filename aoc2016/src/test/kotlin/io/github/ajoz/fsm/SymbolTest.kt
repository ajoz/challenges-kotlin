package io.github.ajoz.fsm

import io.kotlintest.matchers.contain
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.WordSpec

class SymbolTest : WordSpec() {
    init {
        "Symbol" should {
            "use shorter form of toString" {
                Symbol(1).toString() shouldBe "Symbol(1)"
                Symbol('R').toString() shouldBe "Symbol(R)"
                Symbol("TestSymbol").toString() shouldBe "Symbol(TestSymbol)"
            }

            "return a SymbolSet after call to 'or' method with another Symbol" {
                val set = Symbol('A') or Symbol('B')

                set.values.size shouldBe 2
                set.values should contain(Symbol('A'))
                set.values should contain(Symbol('B'))
            }
        }
    }
}