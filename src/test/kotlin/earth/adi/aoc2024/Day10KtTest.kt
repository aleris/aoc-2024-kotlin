package earth.adi.aoc2024

import earth.adi.aoc2024.Day10.Companion.INPUT_DAY_10
import earth.adi.aoc2024.Day10.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day10KtTest {
  companion object {
    private const val INPUT_DAY_10_TEST = "/day_10_test.txt"
  }

  @Test
  fun part1() {
    Day10.part1(INPUT_DAY_10)
  }

  @Test
  fun part2() {
    Day10.part2(INPUT_DAY_10)
  }

  @Test
  fun part1_test1() {
    val map =
        Day10.TopoMap.from(
            """
      ...0...
      ...1...
      ...2...
      6543456
      7.....7
      8.....8
      9.....9
    """
                .trimIndent())
    map.print()
    assertThat(map.peaksFrom(Position(3, 0))).hasSize(2)
  }

  @Test
  fun part1_test2() {
    val map =
        Day10.TopoMap.from(
            """
      ...0...
      ...1...
      ...2...
      6543456
      7...987
      8.....8
      9.....9
    """
                .trimIndent())
    map.print()
    assertThat(map.peaksFrom(Position(3, 0))).hasSize(3)
  }

  @Test
  fun part1_blocks() {
    assertThat(Day10.input(INPUT_DAY_10_TEST).candidateTrailheads()).hasSize(9)
    assertThat(Day10.input(INPUT_DAY_10_TEST).peaksFrom(Position(2, 0))).hasSize(5)
    assertThat(Day10.input(INPUT_DAY_10_TEST).peaksFrom(Position(4, 0))).hasSize(6)
  }

  @Test
  fun part1_test() {
    assertThat(Day10.part1(INPUT_DAY_10_TEST)).isEqualTo(36)
  }

  @Test
  fun part2_test2() {
    val map =
        Day10.TopoMap.from(
            """
      012345
      123456
      234567
      345678
      4.6789
      56789.
    """
                .trimIndent())
    map.print()
    assertThat(map.rating(Position(0, 0))).isEqualTo(227)
  }

  @Test
  fun part2_test() {
    assertThat(Day10.part2(INPUT_DAY_10_TEST)).isEqualTo(81)
  }
}
