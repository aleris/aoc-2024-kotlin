package earth.adi.aoc2024

import earth.adi.aoc2024.Day07.Companion.INPUT_DAY_07
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day07KtTest {
  companion object {
    private const val INPUT_DAY_07_TEST = "/day_07_test.txt"
  }

  @Test
  fun part1() {
    Day07.part1(INPUT_DAY_07)
  }

  @Test
  fun part2() {
    Day07.part2(INPUT_DAY_07)
  }

  @Test
  fun generateOperatorCombinationsPart1() {
    assertThat(
            Day07.generateOperatorCombinations(3, listOf(Day07.Operator.PLUS, Day07.Operator.MUL)))
        .isEqualTo(
            setOf(
                listOf(Day07.Operator.PLUS, Day07.Operator.PLUS, Day07.Operator.PLUS),
                listOf(Day07.Operator.MUL, Day07.Operator.PLUS, Day07.Operator.PLUS),
                listOf(Day07.Operator.PLUS, Day07.Operator.MUL, Day07.Operator.PLUS),
                listOf(Day07.Operator.MUL, Day07.Operator.MUL, Day07.Operator.PLUS),
                listOf(Day07.Operator.PLUS, Day07.Operator.PLUS, Day07.Operator.MUL),
                listOf(Day07.Operator.MUL, Day07.Operator.PLUS, Day07.Operator.MUL),
                listOf(Day07.Operator.PLUS, Day07.Operator.MUL, Day07.Operator.MUL),
                listOf(Day07.Operator.MUL, Day07.Operator.MUL, Day07.Operator.MUL),
            ))
  }

  @Test
  fun generateOperatorCombinationsPart2() {
    assertThat(
            Day07.generateOperatorCombinations(
                2, listOf(Day07.Operator.PLUS, Day07.Operator.MUL, Day07.Operator.CONCAT)))
        .isEqualTo(
            setOf(
                listOf(Day07.Operator.PLUS, Day07.Operator.PLUS),
                listOf(Day07.Operator.MUL, Day07.Operator.PLUS),
                listOf(Day07.Operator.CONCAT, Day07.Operator.PLUS),
                listOf(Day07.Operator.PLUS, Day07.Operator.MUL),
                listOf(Day07.Operator.MUL, Day07.Operator.MUL),
                listOf(Day07.Operator.CONCAT, Day07.Operator.MUL),
                listOf(Day07.Operator.PLUS, Day07.Operator.CONCAT),
                listOf(Day07.Operator.MUL, Day07.Operator.CONCAT),
                listOf(Day07.Operator.CONCAT, Day07.Operator.CONCAT),
            ))
  }

  @Test
  fun resultOf() {
    assertThat(
            Day07.Equation(listOf(13, 14, 15), listOf(Day07.Operator.PLUS, Day07.Operator.MUL))
                .result())
        .isEqualTo((13 + 14) * 15)
  }

  @Test
  fun part1_test() {
    assertThat(Day07.part1(INPUT_DAY_07_TEST)).isEqualTo(3749L)
  }

  @Test
  fun part2_test() {
    assertThat(Day07.part2(INPUT_DAY_07_TEST)).isEqualTo(11387L)
  }
}
