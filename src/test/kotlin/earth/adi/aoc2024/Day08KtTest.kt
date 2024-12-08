package earth.adi.aoc2024

import earth.adi.aoc2024.Day08.Companion.INPUT_DAY_08
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day08KtTest {
  companion object {
    private const val INPUT_DAY_08_TEST = "/day_08_test.txt"
  }

  @Test
  fun part1() {
    Day08.part1(INPUT_DAY_08)
  }

  @Test
  fun part2() {
    Day08.part2(INPUT_DAY_08)
  }

  @Test
  fun collectAntennas() {
    val antennaMap = Day08.input(INPUT_DAY_08_TEST)
    assertThat(antennaMap.collectAntennas())
        .hasSize(2)
        .isEqualTo(
            mapOf(
                '0' to
                    listOf(
                        Day08.Position(x = 4, y = 4),
                        Day08.Position(x = 5, y = 2),
                        Day08.Position(x = 7, y = 3),
                        Day08.Position(x = 8, y = 1)),
                'A' to
                    listOf(
                        Day08.Position(x = 6, y = 5),
                        Day08.Position(x = 8, y = 8),
                        Day08.Position(x = 9, y = 9)),
            ))
  }

  @Test
  fun projectAntiNodes() {
    val antennaMap = Day08.input(INPUT_DAY_08_TEST)
    val antiNodes =
        antennaMap.projectAntiNodes(
            listOf(Day08.Position(x = 5, y = 5), Day08.Position(x = 8, y = 4)))
    assertThat(antiNodes).isEqualTo(listOf(Day08.Position(11, 3), Day08.Position(2, 6)))
  }

  @Test
  fun part1_test() {
    assertThat(Day08.part1(INPUT_DAY_08_TEST)).isEqualTo(14)
  }

  @Test
  fun part2_test() {
    assertThat(Day08.part2(INPUT_DAY_08_TEST)).isEqualTo(34)
  }
}
