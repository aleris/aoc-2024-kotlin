package earth.adi.aoc2024

import earth.adi.aoc2024.Day02.Companion.INPUT_DAY_02
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day02KtTest {
  companion object {
    private const val INPUT_DAY_02_TEST = "/day_02_test.txt"
  }

  @Test
  fun part1() {
    Day02.part1(INPUT_DAY_02)
  }

  @Test
  fun part2() {
    Day02.part2(INPUT_DAY_02)
  }

  @Test
  fun isSafe() {
    val expectedList = listOf(true, false, false, false, false, true)
    Day02.input(INPUT_DAY_02_TEST).forEachIndexed() { index, row ->
      val expected = expectedList[index]
      assertThat(Day02.isSafe(row)).describedAs("$row should be safe=$expected").isEqualTo(expected)
    }
  }

  @Test
  fun isSafeWithDampener() {
    val expectedList = listOf(true, false, false, true, true, true)
    Day02.input(INPUT_DAY_02_TEST).zip(expectedList).forEach { (row, expected) ->
      assertThat(Day02.isSafeWithDampener(row))
          .describedAs("$row should be safe=$expected")
          .isEqualTo(expected)
    }
  }

  @Test
  fun part1_test() {
    assertThat(Day02.part1(INPUT_DAY_02_TEST)).isEqualTo(2)
  }

  @Test
  fun part2_test() {
    assertThat(Day02.part2(INPUT_DAY_02_TEST)).isEqualTo(4)
  }
}
