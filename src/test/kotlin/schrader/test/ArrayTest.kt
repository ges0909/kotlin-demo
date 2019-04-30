package schrader.test

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ArrayTest {

    @Test
    fun emptyArray() {
        val array = emptyArray<Int>()
        Assertions.assertThat(array).isEmpty()
    }
}
