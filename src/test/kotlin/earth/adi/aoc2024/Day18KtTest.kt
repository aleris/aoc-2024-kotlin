package earth.adi.aoc2024

import earth.adi.aoc2024.Day18.Companion.INPUT_DAY_18
import earth.adi.aoc2024.Day18.MemoryMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day18KtTest {
  companion object {
    private const val INPUT_DAY_18_TEST = "/day_18_test.txt"
  }

  @Test
  fun part1() {
    Day18.part1(INPUT_DAY_18, 71, 1024)
  }

  @Test
  fun part2() {
    Day18.part2(INPUT_DAY_18, 71)
  }

  @Test
  fun part1_test() {
    assertThat(Day18.part1(INPUT_DAY_18_TEST, 7, 12)).isEqualTo(22)
  }

  @Test
  fun part1_testSimulate() {
    val incoming = Day18.input(INPUT_DAY_18_TEST)
    val map = MemoryMap(7).simulateCorruption(incoming, 12)
    map.print()
    val path = map.findPath()
    assertThat(path).isNotNull
    println(path!!.steps())
    map.print(path.positions.toSet(), 'O')
  }

  @Test
  fun part2_test() {
    assertThat(Day18.part2(INPUT_DAY_18_TEST, 7)).isEqualTo("6,1")
  }
}
