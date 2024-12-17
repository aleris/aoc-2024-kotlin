package earth.adi.aoc2024

import earth.adi.aoc2024.Day16.Companion.INPUT_DAY_16
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day16KtTest {
  companion object {
    private const val INPUT_DAY_16_TEST = "/day_16_test.txt"
    private const val INPUT_DAY_16_TEST_B = "/day_16_test_b.txt"
    private const val INPUT_DAY_16_TEST_C = "/day_16_test_c.txt"
  }

  @Test
  fun part1() {
    Day16.part1(INPUT_DAY_16)
  }

  @Test
  fun part2() {
    Day16.part2(INPUT_DAY_16)
  }

  @Test
  fun part1_test() {
    assertThat(Day16.part1(INPUT_DAY_16_TEST)).isEqualTo(7036)
  }

  @Test
  fun part1_test_b() {
    assertThat(Day16.part1(INPUT_DAY_16_TEST_B)).isEqualTo(11048)
  }

  @Test
  fun part1_test_c() {
    Day16.part1(INPUT_DAY_16_TEST_C)
  }

  @Test
  fun part2_test() {
    assertThat(Day16.part2(INPUT_DAY_16_TEST)).isEqualTo(45)
  }

  @Test
  fun part2_test_b() {
    assertThat(Day16.part2(INPUT_DAY_16_TEST_B)).isEqualTo(64)
  }
}
