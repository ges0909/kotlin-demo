package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LanguageFeatureTest {

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
        for ((i, v) in array.withIndex()) {
            assertThat(i).isEqualTo(v - 1)
        }
    }

    @Test fun singleExpressionFunction() {
        fun even(v: Int) = v % 2 == 0 // 'return' not allowed
        assertThat(even(1)).isFalse()
        assertThat(even(2)).isTrue()
    }

    @Test fun takeIf_() {
        fun getRandom() = (0..9).random()
        getRandom().takeIf { it < 5 }
                ?.let { assertThat(it).isBetween(0, 4) }
    }

    @Test fun takeUnless_() {
        fun getRandom() = (0..9).random()
        getRandom().takeUnless { it < 5 }
                ?.let { assertThat(it).isBetween(5, 9) }
    }

    @Test fun partialFunction() {
        // partial function cannot be executed for some values
        fun func(name: String): Result {
            if (name.isNotEmpty()) return Success()
            return Failure()
        }
        assertThat(func("non empty")).isEqualToComparingFieldByFieldRecursively(Success())
        assertThat(func("")).isEqualToComparingFieldByFieldRecursively(Failure())
    }
}

sealed class Result(val text: String, val isFailed: Boolean)
class Success(text: String = "") : Result("Success: {$text}", false)
class Failure(text: String = "") : Result("Failure: {$text}", true)
