package earth.adi.aoc2024

import earth.adi.aoc2024.Day15.Companion.INPUT_DAY_15
import earth.adi.aoc2024.Day15.Move
import earth.adi.aoc2024.Day15.WarehouseMap.Companion.ROBOT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day15KtTest {
  companion object {
    private const val INPUT_DAY_15_TEST = "/day_15_test.txt"
    private const val INPUT_DAY_15_TEST_B = "/day_15_test_b.txt"
    private const val INPUT_DAY_15_TEST_C = "/day_15_test_c.txt"
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
  fun part1_widen() {
    val warehouse = Day15.input(INPUT_DAY_15_TEST)
    warehouse.map.print()
    val widenMap = warehouse.withWidenMap().map
    widenMap.print()
    assertThat(widenMap)
        .isEqualTo(
            Day15.WarehouseMap.from(
                """
        ####################
        ##....[]....[]..[]##
        ##............[]..##
        ##..[][]....[]..[]##
        ##....[]@.....[]..##
        ##[]##....[]......##
        ##[]....[]....[]..##
        ##..[][]..[]..[][]##
        ##........[]......##
        ####################
        """
                    .trimIndent()
                    .lines()))
  }

  @Test
  fun part2_moveSteps() {
    val narrowWarehouse = Day15.input(INPUT_DAY_15_TEST_C)
    val warehouse = narrowWarehouse.withWidenMap()
    for (move in warehouse.moves) {
      println(move.char)
      warehouse.moveRobot(move)
      warehouse.map.print()
    }
  }

  @Test
  fun part2_moveOne() {
    val narrowWarehouse = Day15.input(INPUT_DAY_15_TEST_C)
    val warehouse = narrowWarehouse.withWidenMap()
    warehouse.map.print()
    for (i in 0 until 5) {
      val move = warehouse.moves[i]
      println(move.char)
      warehouse.moveRobot(move)
      warehouse.map.print()
    }
    println(Move.UP.char)
    warehouse.moveRobot(Move.UP)
    warehouse.map.print()
  }

  @Test
  fun part2_moveTricky1() {
    val map =
        Day15.WarehouseMap.from(
            """
        ##############
        ##..[]......##
        ##[]........##
        ##.[].......##
        ##.@........##
        ##..........##
        ##############
        """
                .trimIndent()
                .lines())
    val warehouse =
        Day15.Warehouse(map, listOf(Move.UP), map.find(ROBOT), Day15.WideController(map))
    warehouse.map.print()
    warehouse.moveRobot(Move.UP)
    warehouse.map.print()
  }

  @Test
  fun part2_moveTricky2() {
    val map =
        Day15.WarehouseMap.from(
            """
        ####################
        ##....[]....[]..[]##
        ##............[]..##
        ##..[][]....[]..[]##
        ##...[]...[]..[]..##
        ##[]##....[]......##
        ##[][]........[]..##
        ##....@[]..[].[][]##
        ##........[]......##
        ####################
        """
                .trimIndent()
                .lines())
    val warehouse =
        Day15.Warehouse(map, listOf(Move.UP), map.find(ROBOT), Day15.WideController(map))
    warehouse.map.print()
    warehouse.moveRobot(Move.UP)
  }

  @Test
  fun part2_move() {
    val narrowWarehouse = Day15.input(INPUT_DAY_15_TEST_C)
    val warehouse = narrowWarehouse.withWidenMap()
    warehouse.moveRobot()
    assertThat(warehouse.map)
        .isEqualTo(
            Day15.WarehouseMap.from(
                """
        ##############
        ##...[].##..##
        ##...@.[]...##
        ##....[]....##
        ##..........##
        ##..........##
        ##############
        """
                    .trimIndent()
                    .lines()))
  }

  @Test
  fun part2_test() {
    assertThat(Day15.part2(INPUT_DAY_15_TEST)).isEqualTo(9021)
  }
}
