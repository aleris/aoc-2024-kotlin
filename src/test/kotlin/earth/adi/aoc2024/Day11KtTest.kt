package earth.adi.aoc2024

import earth.adi.aoc2024.Day11.Companion.INPUT_DAY_11
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11KtTest {
  companion object {
    private const val INPUT_DAY_11_TEST = "/day_11_test.txt"
  }

  @Test
  fun part1() {
    Day11.part1(INPUT_DAY_11)
  }

  @Test
  fun part2() {
    Day11.part2(INPUT_DAY_11)
  }

  @Test
  fun part1_test1() {
    val list = Day11.input(INPUT_DAY_11_TEST).toMutableList()
    println(list)

    Day11.blink(list)
    println(list)
    assertThat(list).containsExactly(253000, 1, 7)

    Day11.blink(list)
    println(list)
    assertThat(list).containsExactly(253, 0, 2024, 14168)
  }

  @Test
  fun part1_test() {
    assertThat(Day11.part1(INPUT_DAY_11_TEST)).isEqualTo(55312)
  }

  @Test
  fun part2_test1() {
    var map = Day11.input(INPUT_DAY_11_TEST).associate { it to 1L }
    println(map)

    map = Day11.blink(map)
    println(map)
    assertThat(map).containsKeys(253000, 1, 7)

    map = Day11.blink(map)
    println(map)
    assertThat(map).containsKeys(253, 0, 2024, 14168)

    map = Day11.blink(map)
    println(map)
    assertThat(map).containsKeys(512072, 1, 20, 24, 28676032)

    map = Day11.blink(map)
    println(map)
    assertThat(map).containsKeys(512, 72, 2024, 2, 0, 2, 4, 2867, 6032)

    map = Day11.blink(map)
    println(map)
    assertThat(map).containsKeys(1036288, 7, 2, 20, 24, 4048, 1, 4048, 8096, 28, 67, 60, 32)

    map = Day11.blink(map)
    println(map)
    assertThat(map)
        .containsKeys(
            2097446912,
            14168,
            4048,
            2,
            0,
            2,
            4,
            40,
            48,
            2024,
            40,
            48,
            80,
            96,
            2,
            8,
            6,
            7,
            6,
            0,
            3,
            2)
  }
}
