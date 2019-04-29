package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CollectionTest {

    @Nested
    inner class ListTest { // inner classes are non-static nested classes

        @Test fun listOf() {
            val list = listOf(1, 2, 3)
            assertThat(list).isNotEmpty
            assertThat(list.size).isEqualTo(3)
            assertThat(list.first()).isEqualTo(1)
            assertThat(list.last()).isEqualTo(3)
            assertThat(list[1]).isEqualTo(2) // elements of a list can be accessed by their index
        }

        @Test fun emptyList() {
            val list = emptyList<Int>()
            assertThat(list).isEmpty()
        }

        @Test fun mutableListOf() {
            val list = mutableListOf(1, 2, 3)
            list[1] = 0
            assertThat(list[1]).isEqualTo(0)
        }

        @Test fun filter() {
            val list = listOf(1, 2, 3)
            val filtered = list.filter { it % 2 == 0 }
            assertThat(filtered).containsExactly(2)
        }

        @Test fun getOrElse() {
            val list = listOf("one", "two")
            val missing = list.getOrElse(2) { "three" }
            assertThat(missing).isEqualTo("three")
        }
    }

    @Nested
    inner class SetTest {

        @Test fun setOf() {
            val set = setOf(1, 2, 3)
            assertThat(set).containsExactly(1, 2, 3)
        }
    }

    @Nested
    inner class MapTest {

        @Test fun pair() {
            val pair = 1 to "one"
            assertThat(pair.first).isEqualTo(1)
            assertThat(pair.second).isEqualTo("one")
        }

        @Test fun mapOf() {
            val map = mapOf(1 to "one", 2 to "two", 3 to "three")
            assertThat(map[2]).isEqualTo("two") // map values can be accessed by their key
        }

        @Test fun mapOfPAir() {
            val map = mapOf(Pair(1, "one"), Pair(2, "two"), Pair(3, "three"))
            assertThat(map[2]).isEqualTo("two")
        }
    }

    @Nested
    inner class SequenceTest {

        @Test fun generateSequence() {
            val seq = generateSequence(1, { it + 1 }) // sequences represent lazily-evaluated collections
            val list = seq.take(3).toList()
            assertThat(list).containsExactly(1, 2, 3)
        }

        @Test fun fibonacci() {
            val list = fibonacciSequence().take(10).toList()
            assertThat(list).isEqualTo(listOf(1, 1, 2, 3, 5, 8, 13, 21, 34, 55))
        }

        private fun fibonacciSequence(): Sequence<Int> {
            var a = 0
            var b = 1
            fun next(): Int {
                val result = a + b
                a = b
                b = result
                return a
            }
            return generateSequence(::next)
        }
    }
}
