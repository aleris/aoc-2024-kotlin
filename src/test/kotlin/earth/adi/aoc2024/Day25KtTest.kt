package earth.adi.aoc2024

import earth.adi.aoc2024.Day25.Companion.INPUT_DAY_25
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day25KtTest {
  companion object {
    private const val INPUT_DAY_25_TEST = "/day_25_test.txt"
  }

  @Test
  fun part1() {
    Day25.part1(INPUT_DAY_25)
  }

  @Test
  fun part2() {
    Day25.part2(INPUT_DAY_25)
  }

  @Test
  fun part1_test() {
    assertThat(Day25.part1(INPUT_DAY_25_TEST)).isEqualTo(3)
  }

  @Test
  fun part1_parse() {
    val schematics = Day25.input(INPUT_DAY_25_TEST)
    println(schematics)
  }

  @Test
  fun part2_test() {
    assertThat(Day25.part2(INPUT_DAY_25_TEST)).isEqualTo(-1)
  }
}
