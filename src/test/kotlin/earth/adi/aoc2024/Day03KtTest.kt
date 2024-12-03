package earth.adi.aoc2024

import earth.adi.aoc2024.Day03.Companion.INPUT_DAY_03
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day03KtTest {
  companion object {
    private const val INPUT_DAY_03_TEST = "/day_03_test.txt"
    private const val INPUT_DAY_03_TEST_B = "/day_03_test_b.txt"
  }

  @Test
  fun part1() {
    Day03.part1(INPUT_DAY_03)
  }

  @Test
  fun part2() {
    Day03.part2(INPUT_DAY_03)
  }

  @Test
  fun findAllMul() {
    assertThat(Day03.findAllMul("mul(1,2)abcmul(3,4]mul(5,6)mul(7,8)"))
        .containsExactly(Pair(1, 2), Pair(5, 6), Pair(7, 8))
  }

  @Test
  fun sumMul() {
    assertThat(Day03.sumMul(listOf(Pair(1, 2), Pair(5, 6), Pair(7, 8))))
        .isEqualTo(1 * 2 + 5 * 6 + 7 * 8)
  }

  @Test
  fun excludeDisabled() {
    assertThat(
            Day03.excludeDisabled(
                "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"))
        .isEqualTo("xmul(2,4)&mul[3,7]!^?mul(8,5))")
  }

  @Test
  fun part1_test() {
    assertThat(Day03.part1(INPUT_DAY_03_TEST)).isEqualTo(161)
  }

  @Test
  fun part2_test() {
    assertThat(Day03.part2(INPUT_DAY_03_TEST_B)).isEqualTo(48)
  }
}
