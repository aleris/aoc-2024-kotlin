package earth.adi.aoc2024

import earth.adi.aoc2024.Day04.Companion.INPUT_DAY_04
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day04KtTest {
  companion object {
    private const val INPUT_DAY_04_TEST = "/day_04_test.txt"
  }

  @Test
  fun part1() {
    Day04.part1(INPUT_DAY_04)
  }

  @Test
  fun part2() {
    Day04.part2(INPUT_DAY_04)
  }

  @Test
  fun part1_test() {
    assertThat(Day04.part1(INPUT_DAY_04_TEST)).isEqualTo(18)
  }

  @Test
  fun part2_test() {
    assertThat(Day04.part2(INPUT_DAY_04_TEST)).isEqualTo(9)
  }
}
