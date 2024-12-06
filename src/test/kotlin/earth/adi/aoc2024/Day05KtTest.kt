package earth.adi.aoc2024

import earth.adi.aoc2024.Day05.Companion.INPUT_DAY_05
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day05KtTest {
  companion object {
    private const val INPUT_DAY_05_TEST = "/day_05_test.txt"
  }

  @Test
  fun part1() {
    Day05.part1(INPUT_DAY_05)
  }

  @Test
  fun part2() {
    Day05.part2(INPUT_DAY_05)
  }

  @Test
  fun isOrdered() {
    val input = Day05.input(INPUT_DAY_05_TEST)
    assertThat(Day05.isOrdered(listOf(75, 47, 61, 53, 29), input.orderingRules)).isTrue()
    assertThat(Day05.isOrdered(listOf(75, 97, 47, 61, 53), input.orderingRules)).isFalse()
  }

  @Test
  fun reorder() {
    val input = Day05.input(INPUT_DAY_05_TEST)
    assertThat(Day05.reorderIfNeeded(listOf(97, 13, 75, 29, 47), input.orderingRules))
        .isEqualTo(listOf(97, 75, 47, 29, 13))
  }

  @Test
  fun part1_test() {
    assertThat(Day05.part1(INPUT_DAY_05_TEST)).isEqualTo(143)
  }

  @Test
  fun part2_test() {
    assertThat(Day05.part2(INPUT_DAY_05_TEST)).isEqualTo(123)
  }
}
