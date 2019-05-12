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
        println("longRunningTask")
    }

    private suspend fun longRunningTaskWithReturnValue() = withContext(Dispatchers.Default) {
        delay(500)
        println("longRunningTask")
        "longRunningTask"
    }

    /**
     * 'runBlocking', 'launch' and 'async' are coroutine builders for different purposes.
     * The 'runBlocking' builder is designed to bridge regular blocking code to suspending
     * functions; we can use it in main functions and tests.
     */

    @Test fun `launch job and wait on end with join`() = runBlocking {
        val job = launch { longRunningTask() }
        job.join() // 'join' is suspendable
        println("joined")
    }

    @Test fun `launch job and wait on end with runBlocking`() {
        runBlocking {
            val job = launch { longRunningTask() }
        }
        println("joined")
    }

    @Test fun `cancel job`() = runBlocking {
        val job = launch {
            while (true) {
                delay(500)
                println("inner")
            }
        }
        delay(1100)
        job.cancel()
    }

    @Test fun `launch job in user defined scope`() = runBlocking {
        coroutineScope {
            val job = launch {
                while (true) {
                    delay(500)
                    println("inner")
                }
            }
            delay(1100)
            job.cancel()
        }
    }

    @Test fun `launch job an wait on result`() {
        val result = runBlocking {
            val deferred = async { longRunningTaskWithReturnValue() }
            deferred.await()
        }
        assertThat(result).isEqualTo("longRunningTask")
    }

    @Test
    fun `asynchronous processing may look like synchronous one with coroutines`() = runBlocking {
        val r1 = async { longRunningTaskWithReturnValue() }
        val r2 = async { longRunningTaskWithReturnValue() }
        println("${r1.await()}, ${r2.await()}")
    }

    @Test fun `send to and receive from channel`() = runBlocking {
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

    @Test fun `iterate over channel for receive`() = runBlocking {
        val channel = Channel<Int>()
        launch {
            for (i in 0..5) channel.send(i)
            channel.close()
        }
        for (n in channel) println(n)
    }

    @Test
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun `producer and consumer`() = runBlocking {
        val numbers = produce {
            for (i in 0..9) send(i)
        }
        numbers.consumeEach {
            println(it)
        }
    }
}
