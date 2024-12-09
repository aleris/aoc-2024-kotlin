package earth.adi.aoc2024

import earth.adi.aoc2024.Day09.Companion.INPUT_DAY_09
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day09KtTest {
  companion object {
    private const val INPUT_DAY_09_TEST = "/day_09_test.txt"
  }

  @Test
  fun part1() {
    Day09.part1(INPUT_DAY_09)
  }

  @Test
  fun part2() {
    Day09.part2(INPUT_DAY_09)
  }

  @Test
  fun printExample1() {
    val diskMap = Day09.DiskMap.from("12345")
    diskMap.print()
    diskMap.defrag(true)
    diskMap.print()
    println(diskMap.checksum())
  }

  @Test
  fun printExample2() {
    val diskMap = Day09.DiskMap.from("233313312141413140211")
    diskMap.print()
    diskMap.defrag(true)
    diskMap.print()
    println(diskMap.checksum())
  }

  @Test
  fun printExample3() {
    val diskMap = Day09.DiskMap.from("233313312141413140211")
    diskMap.print()
    diskMap.compact(true)
    diskMap.print()
    println(diskMap.checksum())
  }

  @Test
  fun part1_test() {
    assertThat(Day09.part1(INPUT_DAY_09_TEST)).isEqualTo(1928)
  }

  @Test
  fun part2_test() {
    assertThat(Day09.part2(INPUT_DAY_09_TEST)).isEqualTo(2858)
  }
}
