package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DataClassTest {

    data class User(var name: String, val email: String, val country: String)

    @Test fun getter() {
        val user = User("max", "max.mustermann@gmail.com", "de")
        assertThat(user.name).isEqualTo("max")
        assertThat(user.email).isEqualTo("max.mustermann@gmail.com")
        assertThat(user.country).isEqualTo("de")
    }

    @Test fun setter() {
        val user = User("max", "max.mustermann@gmail.com", "de")
        user.name = "maximilian"
        assertThat(user.name).isEqualTo("maximilian")
    }
}
