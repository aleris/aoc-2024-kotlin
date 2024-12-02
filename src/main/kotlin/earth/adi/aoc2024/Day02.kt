package earth.adi.aoc2024

import kotlin.math.abs

class Day02 {

  companion object {
    const val INPUT_DAY_02 = "/day_02.txt"

    fun part1(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = input.count { isSafe(it) }
      println("Day02 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val input = input(inputResourceName)
      val result = input.count { isSafeWithDampener(it) }
      println("Day02 part2 = $result")
      return result
    }

    fun isSafe(row: List<Int>): Boolean {
      val areAllIncreasing = areAllIncreasing(row)
      val areAllDecreasing = areAllDecreasing(row)
      val haveDiffInRange = haveDiffInRange(row, 1..3)
      return (areAllIncreasing || areAllDecreasing) && haveDiffInRange
    }

    fun isSafeWithDampener(row: List<Int>): Boolean {
      row.forEachIndexed { index, _ ->
        val withLevelRemoved = row.filterIndexed { i, _ -> i != index }
        if (isSafe(withLevelRemoved)) {
          return true
        }
      }
      return false
    }

    fun areAllIncreasing(row: List<Int>): Boolean {
      return row.windowed(2).all { it[0] <= it[1] }
    }

    fun areAllDecreasing(row: List<Int>): Boolean {
      return row.windowed(2).all { it[0] >= it[1] }
    }

    fun haveDiffInRange(row: List<Int>, range: IntRange): Boolean {
      return row.windowed(2).all { abs(it[0] - it[1]) in range }
    }

    fun input(inputResourceName: String): List<List<Int>> {
      return readLines(inputResourceName)
          .filter { it.isNotBlank() }
          .map { it.split(" ").map { s -> s.toInt() } }
    }

    private fun readLines(resourceName: String): List<String> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().lines().toList()
    }
  }
}
