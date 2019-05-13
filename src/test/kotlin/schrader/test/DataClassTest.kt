package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DataClassTest {

    private data class Person(val name: String = "Max", var age: Int = 30)

    @Test fun getter() {
        val person = Person()
        assertThat(person.name).isEqualTo("Max")
        assertThat(person.age).isEqualTo(30)
    }

    @Test fun setter() {
        val person = Person()
        person.age = 35
        assertThat(person.age).isEqualTo(35)
    }
}
