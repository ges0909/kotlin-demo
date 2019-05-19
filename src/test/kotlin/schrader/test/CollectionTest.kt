package schrader.test

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CollectionTest {

    data class Person(val name: String, var age: Int)

    @Nested
    inner class ListTest { // inner classes are non-static nested classes

        /**
         * emptyList, listOf, mutableListOf
         */

        @Test fun emptyList() {
            val list = emptyList<Int>()
            assertThat(list).isEmpty()
        }

        @Test fun listOf() {
            val list = listOf(1, 2, 3)
            assertThat(list).isNotEmpty
            assertThat(list.size).isEqualTo(3)
            assertThat(list.first()).isEqualTo(1)
            assertThat(list.last()).isEqualTo(3)
            assertThat(list[1]).isEqualTo(2) // elements of a list can be accessed by their index
        }

        @Test fun mutableListOf() {
            val list = mutableListOf(1, 2, 3)
            list[1] = 0
            assertThat(list[1]).isEqualTo(0)
        }

        /**
         * filter, filterNot, filterIsInstance, filterNotNull
         */

        @Test fun filter_() {
            val list = listOf(Person("Heike", 57), Person("Freddy", 30), Person("Vinz", 20))
            val over30 = list.filter { it.age > 30 }
            assertThat(over30).containsExactly(Person("Heike", 57))
        }

        @Test fun filterNot() {
            val list = listOf(Person("Heike", 57), Person("Freddy", 30), Person("Vinz", 20))
            val notOver30 = list.filterNot { it.age > 30 }
            assertThat(notOver30).containsExactly(Person("Freddy", 30), Person("Vinz", 20))
        }

        @Test fun filterIsInstance() {
            val instances = listOf("Hello", 12, "World", 15.5, null, 500)
            val stringsOnly = instances.filterIsInstance<String>()
            assertThat(stringsOnly).containsExactly("Hello", "World")
        }

        @Test fun filterNotNull() {
            val nulls = listOf(null, "Hello", null, "World", null)
            val notNulls = nulls.filterNotNull()
            assertThat(notNulls).containsExactly("Hello", "World")
        }

        /**
         * filterTo, filterNotTo, filterIsInstanceTo, filterNotNullTo
         */

        @Test fun filterTo_() {
            val listTo = mutableListOf(1, 2, 3, 4, 5)
            val list = listOf(100, 300, 75, 6, 80, 7)
            list.filterTo(listTo) { it < 10 }
            assertThat(listTo).containsExactly(1, 2, 3, 4, 5, 6, 7)
        }

        @Test fun min_() {
            val min = listOf(1, 2, 3).min()
            assertThat(min).isEqualTo(1)
        }

        @Test fun max_() {
            val max = listOf(1, 2, 3).max()
            assertThat(max).isEqualTo(3)
        }

        /**
         * any, all, none
         */

        @Test fun any() {
            val list = listOf(1, 2, 3)
            assertThat(list.any { it < 2 }).isTrue() // true if at least one element matches predicate
            assertThat(list.any { it > 3 }).isFalse()
        }

        @Test fun all_() {
            val list = listOf(1, 2, 3)
            assertThat(list.all { it < 4 }).isTrue() // true if all elements matching predicate
            assertThat(list.all { it > 1 }).isFalse()
        }

        @Test fun none_() {
            val list = listOf(1, 2, 3)
            assertThat(list.none { it > 3 }).isTrue() // true if no element matches predicate
            assertThat(list.none { it > 1 }).isFalse()
        }

        @Test fun anyWithoutPredicate() {
            val list = listOf(1, 2, 3)
            val atLeastOne = list.any() // at least one element
            assertThat(atLeastOne).isTrue()
        }

        @Test fun noneWithoutPredicate() {
            val list = listOf(1, 2, 3)
            val isEmpty = list.none()
            assertThat(isEmpty).isFalse()
        }

        /**
         * getOrElse
         */

        @Test fun getOrElse_() {
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
    inner class PairTest {

        @Test fun pair() {
            val pair = 1 to "one"
            assertThat(pair.first).isEqualTo(1)
            assertThat(pair.second).isEqualTo("one")
        }

        @Test fun pairWithPair() {
            val pair = Pair(1, "one")
            assertThat(pair.first).isEqualTo(1)
            assertThat(pair.second).isEqualTo("one")
        }
    }

    @Nested
    inner class MapTest {

        @Test fun mapOf() {
            val map = mapOf(1 to "one", 2 to "two", 3 to "three")
            assertThat(map[2]).isEqualTo("two") // map values can be accessed by their key
        }

        @Test fun mapOfPair() {
            val map = mapOf(Pair(1, "one"), Pair(2, "two"), Pair(3, "three"))
            assertThat(map[2]).isEqualTo("two")
        }
    }

    @Test fun asSequence_() {
        val persons = listOf(
                Person("Peter", 16),
                Person("Anna", 23),
                Person("Anna", 28),
                Person("Sonya", 39)
        )
        val names = persons
                .asSequence()
                .filter { it.age > 18 }
                .map { it.name }
                .distinct()
                .sorted()
                .toList()
        Assertions.assertThat(names).containsExactly("Anna", "Sonya")
    }
}
