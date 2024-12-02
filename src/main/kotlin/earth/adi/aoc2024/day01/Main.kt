package earth.adi.aoc2024.day01

import kotlin.math.abs

private const val INPUT_DAY_01 = "/day01.txt"

fun main() {
  part1(INPUT_DAY_01)
  part2(INPUT_DAY_01)
}

fun part1(inputResourceName: String): Int {
  val (left, right) = input(inputResourceName)
  val paired = left.sorted().zip(right.sorted())
  val result = paired.sumOf { abs(it.first - it.second) }
  println("Day01 part1 = $result")
  return result
}

fun part2(inputResourceName: String): Int {
  val (left, right) = input(inputResourceName)
  val rightMapCount = right.groupBy { it }.mapValues { it.value.count() }
  val result =
      left.sumOf { leftValue ->
        val rightCount = rightMapCount[leftValue] ?: 0
        leftValue * rightCount
      }
  println("Day01 part2 = $result")
  return result
}

private fun input(inputResourceName: String): Pair<List<Int>, List<Int>> {
  val list =
      readLines(inputResourceName)
          .filter { it.isNotBlank() }
          .map { line ->
            val pair = line.split("   ").map { s -> s.toInt() }
            Pair(pair[0], pair[1])
          }
  return Pair(list.map { it.first }, list.map { it.second })
}

private fun readLines(resourceName: String): List<String> {
  val resource =
      Any::class::class.java.getResourceAsStream(resourceName)
          ?: throw IllegalArgumentException("Resource $resourceName not found")
  return resource.bufferedReader().lines().toList()
}
