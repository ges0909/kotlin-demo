package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LanguageFeatureTest {

    data class User(var name: String = "max", val email: String = "max.mustermann@mail.com", val country: String = "de")

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


    @Nested
    inner class ScopeFunctionTest {

        @Test fun run_() {
            val user = User()
            user.run  {
                val n = "maximilian"
                name = n
            }
        }

        @Test fun with_() {
            val user = User()
            val result = with(user) {
                name = "maximilian"
                "The end." // 'with' returns last expression as result
            }
            assertThat(user.name).isEqualTo("maximilian")
            assertThat(result).isEqualTo("The end.")
        }

        @Test fun apply_() {
            val user = User()
            val result = user.apply {
                name = "maximilian"
            }
            assertThat(user.name).isEqualTo("maximilian")
            assertThat(result).isEqualTo(user) // 'apply' returns the instance itself as result
        }

        @Test fun let_() {

        }

        @Test fun also_() {

        }
    }
}
