package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LanguageFeatureTest {

    data class Person(val name: String = "Max", var age: Int = 30)

    @Test fun if_() {
        val x = (0..10).random()
        val sign = if (x < 0) "negative" else "positive"
        assertThat(sign).isEqualTo("positive")
    }

    @Test fun when_() {
        val pair = when (val n: Any = 2) {
            is Number -> n to "Number"
            is String -> n to "String"
            else -> null to "String"
        }
        assertThat(pair.first).isEqualTo(2)
        assertThat(pair.second).isEqualTo("Number")
    }

    @Test fun for_() {
        val array = arrayOf(1, 2, 3)
        for ((index, value) in array.withIndex()) {
            assertThat(index).isEqualTo(value - 1)
        }
    }

    @Test fun singleExpressionFunction() {
        fun even(v: Int) = v % 2 == 0 // 'return' not allowed
        assertThat(even(1)).isFalse()
        assertThat(even(2)).isTrue()
    }

    /**
     * scope functions
     */

    @Nested
    inner class ScopeFunctionTest {

        /**
         * | n/a         | this  |  it  |
         * |:------------|:-----:|:----:|
         * | result      |  run  | let  |
         * | side effect | apply | also |
         *
         */

        @Test fun run_() {
            val person = Person()
            val result = person.run {
                this.age = 35
                "The end." // 'run' returns last expression as result
            }
            assertThat(person.age).isEqualTo(35)
            assertThat(result).isEqualTo("The end.")
        }

        @Test fun let_() {
            val person = Person()
            val result = person.let {
                it.age = 35
                "The end." // 'let' returns last expression as result
            }
            assertThat(person.age).isEqualTo(35)
            assertThat(result).isEqualTo("The end.")
        }

        @Test fun with_() {
            val person = Person()
            val result = with(person) {
                age = 35
                "The end." // 'with' returns last expression as result
            }
            assertThat(person.age).isEqualTo(35)
            assertThat(result).isEqualTo("The end.")
        }

        @Test fun apply_() {
            val person = Person()
            val result = person.apply {
                age = 35
            }
            assertThat(person.age).isEqualTo(35)
            assertThat(result).isEqualTo(person) // 'apply' returns the instance itself as result
        }

        @Test fun also_() {
            val person = Person()
            val result = person.also {
                it.age = 35
            }
            assertThat(person.age).isEqualTo(35)
            assertThat(result).isEqualTo(person) // 'apply' returns the instance itself as result
        }
    }
}
