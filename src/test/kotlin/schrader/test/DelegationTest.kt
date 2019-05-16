package schrader.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

private val Int.BD: BigDecimal
    get() {
        return this.toBigDecimal()
    }

class DelegationTest {

    @Test fun delegatedProperties() {
        val bd = 10.BD
        assertThat(bd).isEqualTo(10.BD)
    }
}
