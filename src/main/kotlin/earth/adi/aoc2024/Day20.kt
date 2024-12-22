package earth.adi.aoc2024

import java.util.*
import kotlin.math.abs

class Day20 {

  data class Position(val x: Int, val y: Int) {
    fun up(distance: Int): Position = Position(x, y - distance)

    fun down(distance: Int): Position = Position(x, y + distance)

    fun left(distance: Int): Position = Position(x - distance, y)

    fun right(distance: Int): Position = Position(x + distance, y)

    val neighbours: List<Position>
      get() = listOf(up(1), down(1), left(1), right(1))

    fun squareDistanceTo(other: Position) = abs(x - other.x) + abs(y - other.y)
  }

  enum class Cell(val char: Char) {
    EMPTY('.'),
    WALL('#'),
    START('S'),
    END('E'),
  }

  data class Path(val positions: List<Position>) {
    val positionSet = positions.toSet()
    val positionIndex = positions.associate { it to positions.indexOf(it) }

    val steps: Int
      get() = positions.size - 1

    fun contains(position: Position) = positionSet.contains(position)

    fun from(position: Position): Path {
      val index = positionIndex[position] ?: return Path(emptyList())
      return Path(positions.subList(index, positions.size))
    }

    fun distanceOnPath(from: Position, to: Position): Int {
      val fromIndex = positionIndex[from]!!
      val toIndex = positionIndex[to]!!
      return abs(fromIndex - toIndex)
    }

    fun inRangeOf(position: Position, maxDistance: Int): List<Position> {
      return positions
          .filter { it != position }
          .filter { position.squareDistanceTo(it) <= maxDistance }
    }

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

  data class RaceMap(val data: MutableList<MutableList<Cell>>) {
    val width = data[0].size
    val height = data.size
    val start = find(Cell.START) ?: throw IllegalArgumentException("Start not found")
    val end = find(Cell.END) ?: throw IllegalArgumentException("End not found")

    fun isInside(position: Position): Boolean {
      return position.x in 0 until width && position.y in 0 until height
    }

    fun at(position: Position): Cell {
      return data[position.y][position.x]
    }

    fun set(position: Position, value: Cell) {
      data[position.y][position.x] = value
    }

    fun find(cell: Cell): Position? {
      data.forEachIndexed { y, row ->
        row.forEachIndexed { x, value ->
          if (value == cell) {
            return Position(x, y)
          }
        }
      }
      return null
    }

    fun print(cheat: Cheat? = null, overlay: Set<Position> = emptySet(), overlayChar: Char = 'O') {
      val cheatPath =
          if (cheat != null) {
            cheatPath(cheat.startInWall, cheat.end)
                .positions
                .mapIndexed { index, position -> position to (index + 1).toString() }
                .toMap()
          } else {
            null
          }
      print("    ")
      val xAxis = data[0].indices
      xAxis.forEach { print("${if (it/10 == 0) " " else it / 10}") }
      println()
      print("    ")
      xAxis.forEach { print("${it - (it/10) * 10}") }
      println()
      print("----")
      xAxis.forEach { print("-") }
      println()
      data.forEachIndexed { y, row ->
        print("${y.toString().padStart(2, ' ')}| ")
        row.forEachIndexed { x, cell ->
          val position = Position(x, y)
          if (cheatPath != null) {
            if (cheatPath.containsKey(position)) {
              print(cheatPath[position])
            } else {
              print(cell.char)
            }
          } else if (overlay.contains(position)) {
            print(overlayChar)
          } else {
            print(cell.char)
          }
        }
        println()
      }
      println()
    }

    fun findPath(from: Position): Path? {
      val visited = mutableSetOf<Position>()
      val open = Stack<Position>()
      var current = from
      val cameFrom = mutableMapOf<Position, Position>()
      open.push(current)
      while (open.isNotEmpty()) {
        current = open.pop()
        if (current == end) {
          return Path.reconstruct(cameFrom, end)
        }
        visited.add(current)
        accessibleNeighbours(current)
            .filter { !visited.contains(it) }
            .forEach {
              open.push(it)
              cameFrom[it] = current
            }
      }
      return null
    }

    fun possibleCheatsPath(cheatDistance: Int = 2, minimumSavedSteps: Int = 0): Set<Cheat> {
      val path = findPath(start) ?: return emptySet()
      val result = mutableSetOf<Cheat>()
      path.positions.forEachIndexed { index, pathPosition ->
        val distanceOnPath = index
        pathPosition.neighbours.forEach { startingWallPosition ->
          path
              .inRangeOf(startingWallPosition, cheatDistance - 1)
              .filter { it != pathPosition }
              .forEach { cheatEnd ->
                val distanceFromJumpToEnd = path.distanceOnPath(cheatEnd, end)
                val cheatSteps = pathPosition.squareDistanceTo(cheatEnd)
                val actualSavedSteps =
                    path.steps - (distanceOnPath + distanceFromJumpToEnd + cheatSteps)
                if (minimumSavedSteps <= actualSavedSteps) {
                  result.add(Cheat(pathPosition, startingWallPosition, cheatEnd, actualSavedSteps))
                }
              }
        }
      }
      return result
    }

    private fun cheatPath(from: Position, to: Position): Path {
      val positions = mutableListOf<Position>()
      var position = from
      positions.add(from)
      while (position != to) {
        val nextPosition =
            if (position.x == to.x) {
              Position(position.x, position.y + sign(to.y - position.y))
            } else {
              Position(position.x + sign(to.x - position.x), position.y)
            }
        position = nextPosition
        positions.add(position)
      }
      return Path(positions)
    }

    fun accessibleNeighbours(position: Position): List<Position> {
      return position.neighbours.filter { isInside(it) && at(it) != Cell.WALL }
    }

    companion object {
      fun sign(value: Int) = if (value < 0) -1 else if (value > 0) 1 else 0

      fun from(text: String): RaceMap {
        return RaceMap(
            text
                .lines()
                .map { line ->
                  line
                      .map { char ->
                        when (char) {
                          '.' -> Cell.EMPTY
                          '#' -> Cell.WALL
                          'S' -> Cell.START
                          'E' -> Cell.END
                          else -> throw IllegalArgumentException("Unknown cell $char")
                        }
                      }
                      .toMutableList()
                }
                .toMutableList())
      }
    }
  }

  data class Cheat(
      val pathPosition: Position,
      val startInWall: Position,
      val end: Position,
      val savedSteps: Int
  ) {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || javaClass != other.javaClass) return false

      other as Cheat

      if (pathPosition != other.pathPosition) return false
      if (end != other.end) return false

      return true
    }

    override fun hashCode(): Int {
      var result = savedSteps
      result = 31 * result + pathPosition.hashCode()
      result = 31 * result + end.hashCode()
      return result
    }
  }

  companion object {
    const val INPUT_DAY_20 = "/day_20.txt"

    fun part1(inputResourceName: String, savedSteps: Int): Int {
      val raceMap = input(inputResourceName)
      val result = raceMap.possibleCheatsPath(2, savedSteps).size
      println("Day20 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String, savedSteps: Int): Int {
      val raceMap = input(inputResourceName)
      val result = raceMap.possibleCheatsPath(20, savedSteps).size
      println("Day20 part2 = $result")
      return result
    }

    fun input(resourceName: String): RaceMap {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      val text = resource.bufferedReader().readText().trim()

      return RaceMap.from(text)
    }
  }
}
