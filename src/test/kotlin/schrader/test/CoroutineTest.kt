package schrader.test

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CoroutineTest {

    private suspend fun longRunningTask() = withContext(Dispatchers.Default) {
        delay(500) // suspends coroutine
    }

    private suspend fun longRunningTaskWithReturnValue() = withContext(Dispatchers.Default) {
        delay(500)
        "Hallo"
    }

    @Test
    fun launch_join() {
        val job = GlobalScope.launch { longRunningTask() }
        runBlocking {
            job.join()
        }
    }

    @Test
    fun launch_join_() = runBlocking {
        val job = launch(CoroutineName("routine-1")) { longRunningTask() }
        job.join()
    }

    @Test
    fun async_await() {
        val deferred = GlobalScope.async { longRunningTaskWithReturnValue() }
        runBlocking {
            val result = deferred.await()
            assertThat(result).isEqualTo("Hallo")
        }
    }

    @Test
    fun async_await_() = runBlocking {
        val deferred = async { longRunningTaskWithReturnValue() }
        val result = deferred.await()
        assertThat(result).isEqualTo("Hallo")
        Unit
    }

    @Test
    fun looksLikeSequentialCode() = runBlocking {
        val r1 = async { longRunningTaskWithReturnValue() }
        val r2 = async { longRunningTaskWithReturnValue() }
        println("${r1.await()}, ${r2.await()}")
    }

    @Test
    fun channel_() = runBlocking {
        data class Person(val name: String, val age: Int)

        val channel = Channel<Person>()
        launch {
            channel.send(Person("Vinz", 20))
            channel.close()
        }
        val person = channel.receive()
        assertThat(person).isEqualTo(Person("Vinz", 20))
        Unit
    }

    @Test
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun produce_() = runBlocking {
        val numbers = produce {
            for (i in 0..9) send(i)
        }
        numbers.consumeEach {
            println(it)
        }
        Unit
    }
}
