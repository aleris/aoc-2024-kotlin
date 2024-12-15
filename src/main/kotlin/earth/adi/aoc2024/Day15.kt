package earth.adi.aoc2024

class Day15 {

  data class WarehouseMap(val data: MutableList<MutableList<Char>>) {
    fun at(p: Position): Char {
      return data[p.y][p.x]
    }

    fun set(p: Position, c: Char) {
      data[p.y][p.x] = c
    }

    fun find(e: Char): Position {
      return data
          .flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, cell -> if (cell == e) Position(x, y) else null }
          }
          .first()
    }

    fun move(fromPos: Position, move: Move): Boolean {
      val nextPos = fromPos.movedBy(move)
      if (at(nextPos) == Warehouse.WALL) return false
      if (at(nextPos) == Warehouse.BOX) {
        if (move(nextPos, move)) {
          swap(fromPos, nextPos)
          return true
        }
        return false
      }
      // empty space
      swap(fromPos, nextPos)
      return true
    }

    fun boxPositions(): List<Position> {
      return data.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, cell -> if (cell == Warehouse.BOX) Position(x, y) else null }
      }
    }

    fun swap(p1: Position, p2: Position) {
      val temp = at(p1)
      set(p1, at(p2))
      set(p2, temp)
    }

    fun print() {
      data.forEach { row -> println(row.joinToString("")) }
      println()
    }

    companion object {
      fun from(input: List<String>): WarehouseMap {
        return WarehouseMap(input.map { it.toMutableList() }.toMutableList())
      }
    }
  }

  data class Warehouse(val map: WarehouseMap, val moves: List<Move>, var robotPosition: Position) {

    fun moveRobot() {
      moves.forEach { move ->
        if (map.move(robotPosition, move)) {
          robotPosition = robotPosition.movedBy(move)
        }
      }
    }

    companion object {
      const val ROBOT = '@'
      const val BOX = 'O'
      const val WALL = '#'
      const val EMPTY = '.'

      fun from(input: String): Warehouse {
        val warehouseText = input.lines().takeWhile { it.isNotEmpty() }
        val map = WarehouseMap.from(warehouseText)
        val robotPosition = map.find(ROBOT)
        val movesText = input.lines().dropWhile { it.isNotEmpty() }.drop(1).joinToString("")
        val moves = movesText.map { Move.valueOf(it) }
        return Warehouse(map, moves, robotPosition)
      }
    }
  }

  data class Position(val x: Int, val y: Int) {
    fun movedBy(move: Move): Position {
      return Position(x + move.dx, y + move.dy)
    }

    val up: Position
      get() = Position(x, y - 1)

    val right: Position
      get() = Position(x + 1, y)

    val down: Position
      get() = Position(x, y + 1)

    val left: Position
      get() = Position(x - 1, y)

    val gps: Int
      get() = y * 100 + x
  }

  enum class Move(val dx: Int, val dy: Int, val char: Char) {
    UP(0, -1, '^'),
    RIGHT(1, 0, '>'),
    DOWN(0, 1, 'v'),
    LEFT(-1, 0, '<');

    companion object {
      val map = Move.entries.associateBy { it.char }

      fun valueOf(char: Char): Move {
        return map[char] ?: throw IllegalArgumentException("Invalid move $char")
      }
    }
  }

  companion object {
    const val INPUT_DAY_15 = "/day_15.txt"

    fun part1(inputResourceName: String): Int {
      val warehouse = input(inputResourceName)
      warehouse.moveRobot()
      val result = warehouse.map.boxPositions().sumOf { it.gps }
      println("Day15 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val robots = input(inputResourceName)
      val result = 0
      println("Day15 part2 = $result")
      return result
    }

    fun input(resourceName: String): Warehouse {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return Warehouse.from(resource.bufferedReader().readText().trim())
    }
  }
}
