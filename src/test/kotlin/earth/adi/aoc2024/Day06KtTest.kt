package earth.adi.aoc2024

import earth.adi.aoc2024.Day06.Companion.INPUT_DAY_06
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day06KtTest {
  companion object {
    private const val INPUT_DAY_06_TEST = "/day_06_test.txt"
  }

  @Test
  fun part1() {
    Day06.part1(INPUT_DAY_06)
  }

  @Test
  fun part2() {
    Day06.part2(INPUT_DAY_06)
  }

  @Test
  fun print() {
    val area = Day06.input(INPUT_DAY_06_TEST)
    area.print()
    area.guard.move(area.map)
    area.print()
  }

  @Test
  fun part1_test() {
    assertThat(Day06.part1(INPUT_DAY_06_TEST)).isEqualTo(41)
  }

  @Test
  fun part2_test() {
    assertThat(Day06.part2(INPUT_DAY_06_TEST)).isEqualTo(6)
  }
}
