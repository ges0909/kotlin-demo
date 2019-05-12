package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HighOrderFunctionTest {

    /**
     * High order function are functions which takes functions as parameters or return a function.
     */

    private fun <T, U, R> highOrder(a: T, b: U, f: (a: T, b: U) -> R): R {
        return f(a, b)
    }

    @Test
    fun test() {
        val r = highOrder(1, 2) { x, y -> x + y }
        assertThat(r).isEqualTo(3)
    }

    @Test
    fun test2() {
        val r = highOrder("Some", "thing") { x, y -> x + y }
        assertThat(r).isEqualTo("Something")
    }
}
