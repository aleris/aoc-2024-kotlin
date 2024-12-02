package earth.adi.aoc2024.day01

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private const val TEST_INPUT_DAY_01 = "/day01_test.txt"

class MainKtTest {

  @Test
  fun part1() {
    assertThat(part1(TEST_INPUT_DAY_01)).isEqualTo(11)
  }

  @Test
  fun part2() {
    assertThat(part2(TEST_INPUT_DAY_01)).isEqualTo(31)
  }
}
