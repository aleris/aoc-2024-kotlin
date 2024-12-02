package earth.adi.aoc2024

import earth.adi.aoc2024.Day01.Companion.INPUT_DAY_01
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day01KtTest {
  companion object {
    private const val INPUT_DAY_01_TEST = "/day_01_test.txt"
  }

  @Test
  fun part1() {
    Day01.part1(INPUT_DAY_01)
  }

  @Test
  fun part2() {
    Day01.part2(INPUT_DAY_01)
  }

  @Test
  fun part1_test() {
    assertThat(Day01.part1(INPUT_DAY_01_TEST)).isEqualTo(11)
  }

  @Test
  fun part2_test() {
    assertThat(Day01.part2(INPUT_DAY_01_TEST)).isEqualTo(31)
  }
}
