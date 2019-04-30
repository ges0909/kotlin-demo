package schrader.test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CoroutineTest {

    @Test
    fun test_() {
        val hello = "Hello"
        val world = "World"
        val list = mutableListOf<String>()
        GlobalScope.launch(block = {
            delay(500) // suspend coroutine; thread is returned to pool
            // resume coroutine on free thread of pool
            list.add(world)
        })
        list.add(hello)
        Thread.sleep(1000L)
        assertThat(list).isEqualTo(listOf(hello, world))
    }
}
