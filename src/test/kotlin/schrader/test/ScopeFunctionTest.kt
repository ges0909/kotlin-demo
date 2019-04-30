package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * | n/a         | this  |  it  |
 * |:------------|:-----:|:----:|
 * | result      |  run  | let  |
 * | side effect | apply | also |
 *
 */

class ScopeFunctionTest {

    private data class Person(val name: String = "Max", var age: Int = 30)

    @Test
    fun run_() {
        val person = Person()
        val result = person.run {
            this.age = 35
            "The end." // 'run' returns last expression as result
        }
        assertThat(person.age).isEqualTo(35)
        assertThat(result).isEqualTo("The end.")
    }

    @Test
    fun let_() {
        val person = Person()
        val result = person.let {
            it.age = 35
            "The end." // 'let' returns last expression as result
        }
        assertThat(person.age).isEqualTo(35)
        assertThat(result).isEqualTo("The end.")
    }

    @Test
    fun with_() {
        val person = Person()
        val result = with(person) {
            age = 35
            "The end." // 'with' returns last expression as result
        }
        assertThat(person.age).isEqualTo(35)
        assertThat(result).isEqualTo("The end.")
    }

    @Test
    fun apply_() {
        val person = Person()
        val result = person.apply {
            age = 35
        }
        assertThat(person.age).isEqualTo(35)
        assertThat(result).isEqualTo(person) // 'apply' returns the instance itself as result
    }

    @Test
    fun also_() {
        val person = Person()
        val result = person.also {
            it.age = 35
        }
        assertThat(person.age).isEqualTo(35)
        assertThat(result).isEqualTo(person) // 'apply' returns the instance itself as result
    }
}