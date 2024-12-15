package earth.adi.aoc2024

import earth.adi.aoc2024.Day13.Companion.INPUT_DAY_13
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day13KtTest {
  companion object {
    private const val INPUT_DAY_13_TEST = "/day_13_test.txt"
  }

  @Test
  fun part1() {
    Day13.part1(INPUT_DAY_13)
  }

  @Test
  fun part2() {
    Day13.part2(INPUT_DAY_13)
  }

  @Test
  fun part1_input() {
    val input = Day13.input(INPUT_DAY_13_TEST)
    assertThat(input).hasSize(4)

    val first = input[0]
    assertThat(first.buttonA).isEqualTo(Day13.Vec(94L, 34L))
    assertThat(first.buttonB).isEqualTo(Day13.Vec(22L, 67L))
    assertThat(first.prize).isEqualTo(Day13.Vec(8400L, 5400L))
  }

  @Test
  fun part1_solve() {
    val input = Day13.input(INPUT_DAY_13_TEST)
    assertThat(input).hasSize(4)

    assertThat(input[0].solve()).isEqualTo(Day13.Vec(80L, 40L))
    assertThat(input[1].solve()).isNull()
    assertThat(input[2].solve()).isEqualTo(Day13.Vec(38L, 86L))
    assertThat(input[3].solve()).isNull()
  }

  @Test
  fun part1_cost() {
    assertThat(Day13.ClawMachine.cost(Day13.Vec(38L, 86L))).isEqualTo(200)
  }

  @Test
  fun part1_test() {
    assertThat(Day13.part1(INPUT_DAY_13_TEST)).isEqualTo(480)
  }

  @Test
  fun part2_test() {
    val input = Day13.input(INPUT_DAY_13_TEST)

    assertThat(input[0].corrected().solve()).isNull()
    assertThat(input[1].corrected().solve()).isNotNull
    assertThat(input[2].corrected().solve()).isNull()
    assertThat(input[3].corrected().solve()).isNotNull
  }
}
