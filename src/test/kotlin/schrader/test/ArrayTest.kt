package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArrayTest {

    @Test
    fun emptyArray_() {
        val array = emptyArray<Int>()
        assertThat(array).isEmpty()
    }

    @Test
    fun arrayOf_() {
        val array = arrayOf(1, 2, 3)
        assertThat(array).containsExactly(1, 2, 3)
    }

    @Test
    fun intArrayOf_() {
        val array = intArrayOf(1, 2, 3) // array of primitives
        assertThat(array).containsExactly(1, 2, 3)
    }

    @Test
    fun arrayOfNulls_() {
        val array = arrayOfNulls<Number>(3)
        for (i in array.indices) {
            array[i] = i + 1 // late initialization
        }
        assertThat(array).containsExactly(1, 2, 3)
    }

    @Test
    fun arrayConstructorWithInitializerFunction() {
        val array = Array(3) { i -> i + 1 } // constructor with initializer function; i = index
        assertThat(array).containsExactly(1, 2, 3)
    }

    @Test
    fun primitiveArrayConstructorWithInitializerFunction() {
        val array = IntArray(3) { i -> i + 1 }
        assertThat(array).containsExactly(1, 2, 3)
    }
}
