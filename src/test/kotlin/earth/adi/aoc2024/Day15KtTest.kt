package earth.adi.aoc2024

import earth.adi.aoc2024.Day15.Companion.INPUT_DAY_15
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day15KtTest {
  companion object {
    private const val INPUT_DAY_15_TEST = "/day_15_test.txt"
    private const val INPUT_DAY_15_TEST_B = "/day_15_test_b.txt"
  }

  @Test
  fun part1() {
    Day15.part1(INPUT_DAY_15)
  }

  @Test
  fun part2() {
    Day15.part2(INPUT_DAY_15)
  }

  @Test
  fun part1_testB() {
    val warehouse = Day15.input(INPUT_DAY_15_TEST_B)
    warehouse.moveRobot()
    assertThat(warehouse.map)
        .isEqualTo(
            Day15.WarehouseMap.from(
                """
        ########
        #....OO#
        ##.....#
        #.....O#
        #.#O@..#
        #...O..#
        #...O..#
        ########
        """
                    .trimIndent()
                    .lines()))
  }

  @Test
  fun part1_testA() {
    val warehouse = Day15.input(INPUT_DAY_15_TEST)
    warehouse.moveRobot()
    assertThat(warehouse.map)
        .isEqualTo(
            Day15.WarehouseMap.from(
                """
        ##########
        #.O.O.OOO#
        #........#
        #OO......#
        #OO@.....#
        #O#.....O#
        #O.....OO#
        #O.....OO#
        #OO....OO#
        ##########
        """
                    .trimIndent()
                    .lines()))
  }

  @Test
  fun part1_test() {
    assertThat(Day15.part1(INPUT_DAY_15_TEST)).isEqualTo(10092)
  }

  @Test
  fun part2_test() {
    assertThat(Day15.part2(INPUT_DAY_15_TEST)).isEqualTo(-1)
  }
}
