package earth.adi.aoc2024

import earth.adi.aoc2024.Day12.Companion.INPUT_DAY_12
import earth.adi.aoc2024.Day12.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day12KtTest {
  companion object {
    private const val INPUT_DAY_12_TEST = "/day_12_test.txt"

    private val SIMPLE_MAP_1 =
        """
      AAAA
      BBCD
      BBCC
      EEEC
    """
            .trimIndent()

    private val SIMPLE_MAP_2 =
        """
      EEEEE
      EXXXX
      EEEEE
      EXXXX
      EEEEE
    """
            .trimIndent()

    private val SIMPLE_MAP_3 =
        """
      AAAAAA
      AAABBA
      AAABBA
      ABBAAA
      ABBAAA
      AAAAAA
    """
            .trimIndent()
  }

  @Test
  fun part1() {
    Day12.part1(INPUT_DAY_12)
  }

  @Test
  fun part2() {
    Day12.part2(INPUT_DAY_12)
  }

  @Test
  fun part1_discoverArea() {
    assertThat(Day12.GardenMap.from(SIMPLE_MAP_1).discoverArea(Position(0, 0)).data).hasSize(4)
    assertThat(Day12.GardenMap.from(SIMPLE_MAP_1).discoverArea(Position(2, 1)).data).hasSize(4)
    assertThat(Day12.GardenMap.from(SIMPLE_MAP_1).discoverArea(Position(0, 3)).data).hasSize(3)
  }

  @Test
  fun part1_map() {
    assertThat(Day12.GardenMap.from(SIMPLE_MAP_1).map()).hasSize(5)
  }

  @Test
  fun part1_perimeter() {
    val map = Day12.GardenMap.from(SIMPLE_MAP_1).map()

    val areaA = map['A']!![0]
    assertThat(areaA.perimeter()).isEqualTo(10)

    val areaB = map['B']!![0]
    assertThat(areaB.perimeter()).isEqualTo(8)

    val areaC = map['C']!![0]
    assertThat(areaC.perimeter()).isEqualTo(10)
  }

  @Test
  fun part1_test() {
    assertThat(Day12.part1(INPUT_DAY_12_TEST)).isEqualTo(1930)
  }

  @Test
  fun part2_sides() {
    val map = Day12.GardenMap.from(SIMPLE_MAP_1).map()

    val areaA = map['A']!![0]
    assertThat(areaA.sides()).isEqualTo(4)

    val areaB = map['B']!![0]
    assertThat(areaB.sides()).isEqualTo(4)

    val areaC = map['C']!![0]
    assertThat(areaC.sides()).isEqualTo(8)
  }

  @Test
  fun part2_discountCost_1() {
    val map = Day12.GardenMap.from(SIMPLE_MAP_1).map()
    assertThat(map.values.flatMap { it }.sumOf { it.discountCost() }).isEqualTo(80)
  }

  @Test
  fun part2_discountCost_2() {
    val map = Day12.GardenMap.from(SIMPLE_MAP_2).map()
    assertThat(map.values.flatMap { it }.sumOf { it.discountCost() }).isEqualTo(236)
  }

  @Test
  fun part2_discountCost_3() {
    val map = Day12.GardenMap.from(SIMPLE_MAP_3).map()
    assertThat(map.values.flatMap { it }.sumOf { it.discountCost() }).isEqualTo(368)
  }

  @Test
  fun part2_test() {
    assertThat(Day12.part2(INPUT_DAY_12_TEST)).isEqualTo(1206)
  }
}
