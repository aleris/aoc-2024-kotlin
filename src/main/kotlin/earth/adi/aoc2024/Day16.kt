package earth.adi.aoc2024

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal

class Day16 {

  data class MazeMap(val data: List<List<Char>>) {
    fun at(p: Position): Char {
      if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) return WALL
      return data[p.y][p.x]
    }

    val width: Int
      get() = data[0].size

    val height: Int
      get() = data.size

    fun find(e: Char): Position {
      return data
          .flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, cell -> if (cell == e) Position(x, y) else null }
          }
          .first()
    }

    fun findPath(): List<Solution> {
      val start = find(START)
      val visited = mutableMapOf<Arrival, Step>()
      val queue = mutableListOf(Step(null, setOf(), start, Direction.EAST))
      val solutionEndSteps = mutableListOf<Step>()
      while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        val currentArrival = Arrival(current.position, current.facing)
        var step: Step? = null
        if (visited.contains(currentArrival)) {
          val best = visited[currentArrival]!!
          if (current.cost < best.cost) {
            step = current
            visited[currentArrival] = current
          } else if (current.cost == best.cost) {
            step = current
            visited[currentArrival] =
                Step(best, best.altPrev + current.altPrev, best.position, best.facing)
          } else {
            step = null
          }
        } else {
          visited.put(currentArrival, current)
          step = current
        }

        if (step == null) continue

        step.position.neighbors
            .filter { at(it) == END }
            .map {
              val facing = current.position.directionTo(it)
              Step(current, setOf(Arrival(current.position, facing)), it, facing)
            }
            .forEach { solutionEndSteps.add(it) }

        step.position.neighbors
            .filter { at(it) != WALL && at(it) != END }
            .map {
              val facing = current.position.directionTo(it)
              Step(current, setOf(Arrival(current.position, facing)), it, facing)
            }
            .forEach { queue.add(it) }
      }
      val solutions = solutionEndSteps.map { Solution.from(it) }
      return solutions
    }

    fun print() {
      data.forEach { row -> println(row.joinToString("")) }
      println()
    }

    fun print(terminal: Terminal, index: Int, solution: Solution) {
      terminal.println(
          "[$index] steps: ${solution.steps.size}, turns: ${solution.turns}, cost: ${solution.cost} ->")
      val pos = solution.steps.values.flatMap { it.altPrev.map { it.position } + it.position }
      data.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
          val position = Position(x, y)
          if (pos.contains(position) && c != START && c != END) {
            //            terminal.print(brightWhite("${solution.steps[position]?.facing?.char ?:
            // START}"))
            terminal.print(brightWhite("O"))
          } else
              when (c) {
                WALL -> terminal.print(red("$c"))
                START -> terminal.print(yellow("$c"))
                END -> terminal.print(green("$c"))
                EMPTY -> terminal.print(" ")
                else -> terminal.print(white("$c"))
              }
        }
        terminal.println()
      }
      terminal.println()
    }

    companion object {
      const val START = 'S'
      const val WALL = '#'
      const val EMPTY = '.'
      const val END = 'E'

      fun from(input: String): MazeMap {
        return MazeMap(input.lines().map { it.toList() })
      }
    }
  }

  data class Arrival(val position: Position, val facing: Direction)

  enum class Direction(val char: Char) {
    NORTH('^'),
    EAST('>'),
    SOUTH('v'),
    WEST('<'),
    ;

    fun rotation90DegreesCountTo(to: Direction): Int {
      val diff1Raw = to.ordinal - ordinal
      val diff1 = if (diff1Raw < 0) diff1Raw + 4 else diff1Raw
      val diff2Raw = ordinal - to.ordinal
      val diff2 = if (diff2Raw < 0) diff2Raw + 4 else diff2Raw
      return minOf(diff1, diff2)
    }
  }

  data class Step(
      val previous: Step?,
      val altPrev: Set<Arrival>,
      val position: Position,
      val facing: Direction,
  ) {

    val cost: Int
      get() {
        val previousCost = previous?.cost ?: -1
        return previousCost + 1 + rotation90DegreesCount * 1000
      }

    val rotation90DegreesCount: Int
      get() = if (previous == null) 0 else facing.rotation90DegreesCountTo(previous.facing)
  }

  data class Solution(
      val steps: Map<Position, Step>,
      val turns: Int,
      val cost: Int,
  ) {
    companion object {
      fun from(endStep: Step): Solution {
        val solutionSteps = mutableMapOf<Position, Step>()
        var i: Step? = endStep
        var turns = 0
        var steps = -1
        while (i != null) {
          solutionSteps[i.position] = i
          turns += i.rotation90DegreesCount
          steps++
          i = i.previous
        }
        return Solution(solutionSteps, turns, endStep.cost)
      }
    }
  }

  data class Position(val x: Int, val y: Int) {
    fun directionTo(position: Position): Direction {
      return when {
        position.y < y -> Direction.NORTH
        position.x > x -> Direction.EAST
        position.y > y -> Direction.SOUTH
        else -> Direction.WEST
      }
    }

    val up: Position
      get() = Position(x, y - 1)

    val right: Position
      get() = Position(x + 1, y)

    val down: Position
      get() = Position(x, y + 1)

    val left: Position
      get() = Position(x - 1, y)

    val neighbors: List<Position>
      get() = listOf(up, right, down, left)
  }

  companion object {
    const val INPUT_DAY_16 = "/day_16.txt"

    fun part1(inputResourceName: String): Int {
      val mazeMap = input(inputResourceName)
      val solutions = mazeMap.findPath()
      val terminal = Terminal()
      solutions.forEachIndexed { i, sol -> mazeMap.print(terminal, i, sol) }
      val result = solutions.minOf { it.cost }
      println("Day16 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val mazeMap = input(inputResourceName)
      val solutions = mazeMap.findPath()
      val terminal = Terminal()
      solutions.forEachIndexed { i, sol -> mazeMap.print(terminal, i, sol) }
      val sorted = solutions.sortedBy { it.cost }
      val best = sorted.filter { it.cost == sorted.first().cost }
      val bestPositions = best.flatMap { it.steps.values.map { it.position } }.toSet()
      val result = bestPositions.size
      println("Day16 part2 = $result")
      return result
    }

    fun input(resourceName: String): MazeMap {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return MazeMap.from(resource.bufferedReader().readText().trim())
    }
  }
}
