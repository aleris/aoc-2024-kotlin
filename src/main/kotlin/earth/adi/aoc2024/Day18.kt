package earth.adi.aoc2024

import java.util.PriorityQueue
import kotlin.math.abs

class Day18 {
  data class Position(val x: Int, val y: Int) {
    fun up(): Position = Position(x, y - 1)

    fun down(): Position = Position(x, y + 1)

    fun left(): Position = Position(x - 1, y)

    fun right(): Position = Position(x + 1, y)

    fun neighbours() = listOf(up(), down(), left(), right())

    fun squareDistanceTo(other: Position) = abs(x - other.x) + abs(y - other.y)

    fun distanceTo(other: Position) = squareDistanceTo(other)
  }

  enum class Cell(val char: Char) {
    EMPTY('.'),
    CORRUPTED('#'),
  }

  data class Path(val positions: List<Position>) {
    fun steps() = positions.size - 1

    companion object {
      fun reconstruct(comeFrom: Map<Position, Position>, end: Position): Path {
        val result = mutableListOf(end)
        var position = end
        while (comeFrom.containsKey(position)) {
          position = comeFrom[position]!!
          result.add(position)
        }
        return Path(result.reversed())
      }
    }
  }

  data class MemoryMap(val size: Int) {
    val data: MutableList<MutableList<Cell>> =
        MutableList(size) { MutableList(size) { Cell.EMPTY } }
    val start = Position(0, 0)
    val exit = Position(size - 1, size - 1)

    fun isInside(position: Position): Boolean {
      return position.x in 0 until size && position.y in 0 until size
    }

    fun at(position: Position): Cell {
      return data[position.y][position.x]
    }

    fun set(position: Position, value: Cell) {
      data[position.y][position.x] = value
    }

    fun print(overlay: Set<Position> = emptySet(), overlayChar: Char = 'X') {
      data.forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
          if (overlay.contains(Position(x, y))) {
            print(overlayChar)
          } else {
            print(cell.char)
          }
        }
        println()
      }
      println()
    }

    fun corrupt(position: Position) {
      set(position, Cell.CORRUPTED)
    }

    fun simulateCorruption(incoming: List<Position>, count: Int): MemoryMap {
      incoming.take(count).forEach { corrupt(it) }
      return this
    }

    fun findPath(): Path? {
      val comeFrom = mutableMapOf<Position, Position>()
      val gScore = mutableMapOf<Position, Int>()
      gScore[start] = 0
      val fScore = mutableMapOf<Position, Int>()
      fScore[start] = start.squareDistanceTo(exit)
      val openSet = PriorityQueue<Position>(compareBy { fScore[it] })
      openSet.add(start)
      while (openSet.isNotEmpty()) {
        val current = openSet.remove()
        if (current == exit) {
          return Path.reconstruct(comeFrom, exit)
        }

        accessibleNeighbours(current).forEach { neighbour ->
          val tentativeGScore = gScore[current]!! + current.squareDistanceTo(neighbour)
          if (tentativeGScore < gScore.getOrDefault(neighbour, Int.MAX_VALUE)) {
            comeFrom[neighbour] = current
            gScore[neighbour] = tentativeGScore
            fScore[neighbour] = tentativeGScore + neighbour.squareDistanceTo(exit)
            if (!openSet.contains(neighbour)) {
              openSet.add(neighbour)
            }
          }
        }
      }
      return null
    }

    fun accessibleNeighbours(position: Position): List<Position> {
      return position.neighbours().filter { isInside(it) && at(it) == Cell.EMPTY }
    }
  }

  companion object {
    const val INPUT_DAY_18 = "/day_18.txt"

    fun part1(inputResourceName: String, size: Int, count: Int): Long {
      val incoming = input(inputResourceName)
      val map = MemoryMap(size).simulateCorruption(incoming, count)
      val path = map.findPath()
      if (path == null) {
        println("Day18 part1 = FAILURE")
        return -1
      }
      val result = path.steps().toLong()
      println("Day18 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String, size: Int): String {
      val incoming = input(inputResourceName)
      val map = MemoryMap(size)
      for (i in 0 until incoming.size) {
        map.corrupt(incoming[i])
        if (map.findPath() == null) {
          println("Day18 part2 = ${incoming[i].x},${incoming[i].y}")
          return "${incoming[i].x},${incoming[i].y}"
        }
      }
      println("Day18 part2 = FAILURE")
      return ""
    }

    fun input(resourceName: String): List<Position> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource
          .bufferedReader()
          .readLines()
          .filter { it.isNotBlank() }
          .map { it.split(",").map { it.toInt() }.let { Position(it[0], it[1]) } }
    }
  }
}
