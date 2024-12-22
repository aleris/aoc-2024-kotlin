package earth.adi.aoc2024

import earth.adi.aoc2024.Day20.Companion.INPUT_DAY_20
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day20KtTest {
  companion object {
    private const val INPUT_DAY_20_TEST = "/day_20_test.txt"
  }

  @Test
  fun part1() {
    Day20.part1(INPUT_DAY_20, 100)
  }

  @Test
  fun part2() {
    Day20.part2(INPUT_DAY_20, 100)
  }

  @Test
  fun part1_printMap() {
    val raceMap = Day20.input(INPUT_DAY_20)
    raceMap.print()
    val path = raceMap.findPath(raceMap.start)!!
    raceMap.print(null, path.positionSet)
  }

  @Test
  fun part1_test() {
    assertThat(Day20.part1(INPUT_DAY_20_TEST, 64)).isEqualTo(1)
    assertThat(Day20.part1(INPUT_DAY_20_TEST, 40)).isEqualTo(2)
    assertThat(Day20.part1(INPUT_DAY_20_TEST, 8)).isEqualTo(14)
  }

  @Test
  fun part2_test() {
    val raceMap = Day20.input(INPUT_DAY_20_TEST)
    raceMap.print()
    assertThat(Day20.part2(INPUT_DAY_20_TEST, 76)).isEqualTo(3)
    assertThat(Day20.part2(INPUT_DAY_20_TEST, 74)).isEqualTo(3 + 4)
    assertThat(Day20.part2(INPUT_DAY_20_TEST, 72)).isEqualTo(3 + 4 + 22)

    assertThat(Day20.part2(INPUT_DAY_20_TEST, 50))
        .isEqualTo(32 + 31 + 29 + 39 + 25 + 23 + 20 + 19 + 12 + 14 + 12 + 22 + 4 + 3)
  }

  @Test
  fun part2_printPossibleCheatsPath() {
    val raceMap = Day20.input(INPUT_DAY_20_TEST)
    raceMap.print()
    val possibleCheatsPath = raceMap.possibleCheatsPath(20, 76)
    println("found=${possibleCheatsPath.size}")
    println()
    possibleCheatsPath.forEachIndexed { index, cheat ->
      println("[${index + 1}]")
      println(cheat)
      raceMap.print(cheat)
    }
  }
}
