package schrader.test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CoroutineTest {

    @Test fun test_() {
        var result: String = ""
        GlobalScope.launch(block = {
            delay(500)
            result = result.plus("World!")
        })
        result = result.plus("Hello, ")
        Thread.sleep(1000L)
        assertThat(result).isEqualTo("Hello, World!")
    }
}
