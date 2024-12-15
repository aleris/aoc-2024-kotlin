package earth.adi.aoc2024

import earth.adi.aoc2024.Day14.Companion.INPUT_DAY_14
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14KtTest {
  companion object {
    private const val INPUT_DAY_14_TEST = "/day_14_test.txt"
  }

  @Test
  fun part1() {
    Day14.part1(INPUT_DAY_14, Day14.Space(101, 103))
  }

  @Test
  fun part2() {
    Day14.part2(INPUT_DAY_14)
  }

  @Test
  fun part1_test() {
    assertThat(Day14.part1(INPUT_DAY_14_TEST, Day14.Space(11, 7))).isEqualTo(12)
  }
}
