package earth.adi.aoc2024

import kotlin.math.min

class Day19 {

  data class Onsen(val towels: List<String>, val designs: List<String>)

  data class DesignSolver(val towels: List<String>) {
    val towelSet = towels.toSet()
    val maxTowelLength = towels.maxOf { it.length }

    fun isDesignPossible(design: String): Boolean {
      if (towelSet.contains(design)) {
        return true
      }
      for (endSlice in 1..min(maxTowelLength, design.length)) {
        val slice = design.slice(0 until endSlice)
        val remaining = design.slice(endSlice until design.length)
        if (towelSet.contains(slice) && isDesignPossible(remaining)) {
          return true
        }
      }
      return false
    }
  }

  companion object {
    const val INPUT_DAY_19 = "/day_19.txt"

    fun part1(inputResourceName: String): Int {
      val input = input(inputResourceName)
      println("towels:\n${input.towels.joinToString(" ")}")
      val designSolver = DesignSolver(input.towels)
      val result = input.designs.count { designSolver.isDesignPossible(it) }
      println("Day19 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Long {
      val input = input(inputResourceName)
      val result = 0L
      println("Day19 part2 = $result")
      return result
    }

    fun input(resourceName: String): Onsen {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      val lines = resource.bufferedReader().lines().toList()

      val towels = lines.takeWhile { it.isNotBlank() }.first().split(", ")
      val designs = lines.dropWhile { it.isNotBlank() }.dropWhile { it.isBlank() }.toList()
      return Onsen(towels, designs)
    }
  }
}