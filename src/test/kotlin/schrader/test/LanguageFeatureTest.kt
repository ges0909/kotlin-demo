package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LanguageFeatureTest {

    @Test
    fun if_() {
        val x = (0..10).random()
        val sign = if (x < 0) "negative" else "positive"
        assertThat(sign).isEqualTo("positive")
    }

    @Test
    fun when_() {
        val pair = when (val n: Any = 2) {
            is Number -> n to "Number"
            is String -> n to "String"
            else -> null to "String"
        }
        assertThat(pair.first).isEqualTo(2)
        assertThat(pair.second).isEqualTo("Number")
    }

    @Test
    fun for_() {
        val array = arrayOf(1, 2, 3)
        for ((index, value) in array.withIndex()) {
            assertThat(index).isEqualTo(value - 1)
        }
    }

    @Test
    fun singleExpressionFunction() {
        fun even(v: Int) = v % 2 == 0 // 'return' not allowed
        assertThat(even(1)).isFalse()
        assertThat(even(2)).isTrue()
    }

    @Test
    fun click() {
        Button().click()
    }

    interface ActionListener {
        fun click()
    }

    class Button : ActionListener {
        override fun click() = println("Button clicked")
    }

    @Nested
    inner class Miscellaneous {

        @JvmName("sortStrings")
        fun sort(strings: List<String>) { // will be compiled to 'sort(strings: List)' because if type erasure
        }

        @JvmName("sortInts")
        fun sort(ints: List<Int>) { // will be also compiled to 'sort(strings: List)' because if type erasure
        }
    }
}
