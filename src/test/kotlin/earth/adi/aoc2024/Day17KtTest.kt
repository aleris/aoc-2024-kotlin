package earth.adi.aoc2024

import earth.adi.aoc2024.Day17.Companion.INPUT_DAY_17
import earth.adi.aoc2024.Day17.Computer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day17KtTest {
  companion object {
    private const val INPUT_DAY_17_TEST = "/day_17_test.txt"
    private const val INPUT_DAY_17_TEST_B = "/day_17_test_b.txt"
    private const val INPUT_DAY_17_TEST_C = "/day_17_test_c.txt"
  }

  @Test
  fun part1() {
    Day17.part1(INPUT_DAY_17)
  }

  @Test
  fun part2() {
    Day17.part2(INPUT_DAY_17)
  }

  @Test
  fun part1_test_1() {
    val computer =
        Computer.from(
            """
      Register A: 10
      Register B: 0
      Register C: 9

      Program: 2,6
    """
                .trimIndent())
    computer.run()
    assertThat(computer.registers.B).isEqualTo(1)
  }

  @Test
  fun part1_test_2() {
    val computer =
        Computer.from(
            """
      Register A: 10
      Register B: 0
      Register C: 0

      Program: 5,0,5,1,5,4
    """
                .trimIndent())
    computer.run()
    assertThat(computer.output.joinToString()).isEqualTo("0,1,2")
  }

  @Test
  fun part1_test_3() {
    val computer =
        Computer.from(
            """
      Register A: 2024
      Register B: 0
      Register C: 0

      Program: 0,1,5,4,3,0
    """
                .trimIndent())
    computer.run()
    assertThat(computer.output.joinToString()).isEqualTo("4,2,5,6,7,7,7,7,3,1,0")
    assertThat(computer.registers.A).isEqualTo(0)
  }

  @Test
  fun part1_test_4() {
    val computer =
        Computer.from(
            """
      Register A: 0
      Register B: 29
      Register C: 0

      Program: 1,7
    """
                .trimIndent())
    computer.run()
    assertThat(computer.registers.B).isEqualTo(26)
  }

  @Test
  fun part1_test_5() {
    val computer =
        Computer.from(
            """
      Register A: 0
      Register B: 2024
      Register C: 43690

      Program: 4,0
    """
                .trimIndent())
    computer.run()
    assertThat(computer.registers.B).isEqualTo(44354)
  }

  @Test
  fun part1_test() {
    assertThat(Day17.part1(INPUT_DAY_17_TEST)).isEqualTo("4,6,3,5,6,3,5,2,1,0")
  }

  @Test
  fun part1_test_b() {
    assertThat(Day17.part1(INPUT_DAY_17_TEST_B)).isEqualTo("3,1,5,3,7,4,2,7,5")
  }

  @Test
  fun part2_test() {
    assertThat(Day17.part2(INPUT_DAY_17_TEST_C)).isEqualTo(117440)
  }
}
