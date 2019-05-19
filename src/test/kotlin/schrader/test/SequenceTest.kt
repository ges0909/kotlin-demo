package schrader.test

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SequenceTest {

    @Test fun `generate a sequence (lazy list)`() {
        val seq = generateSequence(1, { it + 1 }) // sequences represent lazily-evaluated collections
        val list = seq.take(3).toList()
        Assertions.assertThat(list).containsExactly(1, 2, 3)
    }

    @Test fun `toggle execution between yield and take`() {
        val seq: Sequence<Char> = sequence {
            var c = 'A'
            while (true) { // though there is an endless loop ...
                yield(c) // ... 'yield' adds element to sequence and returns control to caller
                c++
            }
        }
        val list = seq.take(5).toList() // sequence elements are generated on demand
        assertThat(list).containsExactlyInAnyOrder('A', 'B', 'C', 'D', 'E')
    }

    @Nested
    inner class ConsumerProducerWithChannel {

        private suspend fun produce(channel: SendChannel<Char>) {
            sequence {
                var c = 'A'
                while (true) {
                    yield(c)
                    c++
                }
            }
        }

        private suspend fun consume(id: String, channel: ReceiveChannel<Char>) {
            repeat(2) {
                val c = channel.receive()
                delay(100L) // "suspendable"
            }
        }

        @Test fun `channel example`() {
            runBlocking {
                val channel = Channel<Char>(Channel.RENDEZVOUS /*buffer size = 1*/)
                launch {
                    produce(channel) // suspends if buffer is full
                }
                launch {
                    consume("A", channel) // suspends if buffer is empty
                }
                launch {
                    consume("B", channel)
                }
            }
        }
    }
}
