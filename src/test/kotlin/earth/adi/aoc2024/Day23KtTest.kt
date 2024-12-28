package earth.adi.aoc2024

import earth.adi.aoc2024.Day23.Companion.INPUT_DAY_23
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day23KtTest {
  companion object {
    private const val INPUT_DAY_23_TEST = "/day_23_test.txt"
  }

  @Test
  fun part1() {
    Day23.part1(INPUT_DAY_23)
  }

  @Test
  fun part2() {
    Day23.part2(INPUT_DAY_23)
  }

  @Test
  fun part1_test() {
    assertThat(Day23.part1(INPUT_DAY_23_TEST)).isEqualTo(7)
  }

  @Test
  fun part1_connected() {
    val input = Day23.input(INPUT_DAY_23_TEST)
    val graph = Day23.Graph.from(input)
    val connected = graph.findTrianglesT()
    assertThat(connected).hasSize(7)
  }

  @Test
  fun part2_test() {
    assertThat(Day23.part2(INPUT_DAY_23_TEST)).isEqualTo("co,de,ka,ta")
  }

  @Test
  fun part2_cliqueOf() {
    val input = Day23.input(INPUT_DAY_23_TEST)
    val graph = Day23.Graph.from(input)
    val clique = graph.cliqueOf("ka")
    assertThat(clique).containsExactlyInAnyOrder("co", "de", "ka", "ta")
  }
}
