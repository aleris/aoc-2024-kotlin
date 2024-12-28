package earth.adi.aoc2024

import earth.adi.aoc2024.Day24.Companion.INPUT_DAY_24
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day24KtTest {
  companion object {
    private const val INPUT_DAY_24_TEST = "/day_24_test.txt"
    private const val INPUT_DAY_24_TEST_B = "/day_24_test_b.txt"
  }

  @Test
  fun part1() {
    Day24.part1(INPUT_DAY_24)
  }

  @Test
  fun part2() {
    Day24.part2(INPUT_DAY_24)
  }

  @Test
  fun part1_test() {
    assertThat(Day24.part1(INPUT_DAY_24_TEST)).isEqualTo(4)
  }

  @Test
  fun part1_test_b() {
    assertThat(Day24.part1(INPUT_DAY_24_TEST_B)).isEqualTo(2024)
  }

  @Test
  fun part1_parse() {
    val text = Day24.input(INPUT_DAY_24_TEST)
    val circuit = Day24.Circuit.from(text)
    println(circuit)
  }

  @Test
  fun part1_evaluate() {
    val text = Day24.input(INPUT_DAY_24_TEST)
    val circuit = Day24.Circuit.from(text)
    val out = circuit.evaluateUntilComplete()
    println(out)
  }

  @Test
  fun part2_test() {
    assertThat(Day24.part2(INPUT_DAY_24_TEST)).isEqualTo(-1)
  }
}
