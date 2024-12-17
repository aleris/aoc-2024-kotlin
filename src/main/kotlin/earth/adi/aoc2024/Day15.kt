package earth.adi.aoc2024

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import earth.adi.aoc2024.Day15.WarehouseMap.Companion.BOX
import earth.adi.aoc2024.Day15.WarehouseMap.Companion.EMPTY
import earth.adi.aoc2024.Day15.WarehouseMap.Companion.ROBOT
import earth.adi.aoc2024.Day15.WarehouseMap.Companion.WALL
import earth.adi.aoc2024.Day15.WarehouseMap.Companion.WIDE_BOX_H1
import earth.adi.aoc2024.Day15.WarehouseMap.Companion.WIDE_BOX_H2

class Day15 {

  data class WarehouseMap(val data: MutableList<MutableList<Char>>) {
    fun at(p: Position): Char {
      if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) return WALL
      return data[p.y][p.x]
    }

    fun set(p: Position, c: Char) {
      data[p.y][p.x] = c
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

    fun print() {
      data.forEach { row -> println(row.joinToString("")) }
      println()
    }

    fun print(terminal: Terminal) {
      data.forEach { row ->
        row.forEach { c ->
          when (c) {
            WALL -> terminal.print(red("$c"))
            BOX -> terminal.print(blue("$c"))
            ROBOT -> terminal.print(brightYellow("$c"))
            EMPTY -> terminal.print(" ")
            WIDE_BOX_H1 -> terminal.print(blue("$c"))
            WIDE_BOX_H2 -> terminal.print(blue("$c"))
            else -> terminal.print(white("$c"))
          }
        }
        terminal.println()
      }
      terminal.println()
    }

    fun widen(): WarehouseMap {
      val newData = mutableListOf<MutableList<Char>>()
      for (y in 0 until height) {
        val newRow = mutableListOf<Char>()
        for (x in 0 until width) {
          val cell = at(Position(x, y))
          if (cell == BOX) {
            newRow.add(WIDE_BOX_H1)
            newRow.add(WIDE_BOX_H2)
          } else if (cell == ROBOT) {
            newRow.add(ROBOT)
            newRow.add(EMPTY)
          } else {
            newRow.add(cell)
            newRow.add(cell)
          }
        }
        newData.add(newRow)
      }
      return WarehouseMap(newData)
    }

    companion object {
      const val ROBOT = '@'
      const val BOX = 'O'
      const val WALL = '#'
      const val EMPTY = '.'
      const val WIDE_BOX_H1 = '['
      const val WIDE_BOX_H2 = ']'

      fun from(input: List<String>): WarehouseMap {
        return WarehouseMap(input.map { it.toMutableList() }.toMutableList())
      }
    }
  }

  interface MoveController {
    fun move(fromPos: Position, move: Move): Boolean

    fun boxPositions(): List<Position>
  }

  class NarrowController(val map: WarehouseMap) : MoveController {
    override fun move(fromPos: Position, move: Move): Boolean {
      val nextPos = fromPos.movedBy(move)
      if (map.at(nextPos) == WALL) return false
      if (map.at(nextPos) == BOX) {
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

    override fun boxPositions(): List<Position> {
      return map.data.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, cell -> if (cell == BOX) Position(x, y) else null }
      }
    }

    fun swap(p1: Position, p2: Position) {
      val temp = map.at(p1)
      map.set(p1, map.at(p2))
      map.set(p2, temp)
    }
  }

  class WideController(val map: WarehouseMap) : MoveController {
    override fun move(fromPos: Position, move: Move): Boolean {
      if (map.at(fromPos) == EMPTY) return true
      if (!canMove(fromPos, move)) {
        return false
      }
      if (WideBox.isWideBox(map.at(fromPos))) {
        val wideBoxPos = locateWideBox(fromPos)
        val nextWideBoxPos = wideBoxPos.movedBy(move)
        if (move == Move.LEFT) {
          if (WideBox.isWideBox(map.at(nextWideBoxPos.h1))) {
            move(nextWideBoxPos.h1, move)
          }
          swap(wideBoxPos.h1, nextWideBoxPos.h1)
          swap(wideBoxPos.h2, nextWideBoxPos.h2)
        } else if (move == Move.RIGHT) {
          if (WideBox.isWideBox(map.at(nextWideBoxPos.h2))) {
            move(nextWideBoxPos.h2, move)
          }
          swap(wideBoxPos.h2, nextWideBoxPos.h2)
          swap(wideBoxPos.h1, nextWideBoxPos.h1)
        } else {
          if (WideBox.isWideBox(map.at(nextWideBoxPos.h1)) ||
              WideBox.isWideBox(map.at(nextWideBoxPos.h2))) {
            move(nextWideBoxPos.h1, move)
            move(nextWideBoxPos.h2, move)
          }
          swap(wideBoxPos.h1, nextWideBoxPos.h1)
          swap(wideBoxPos.h2, nextWideBoxPos.h2)
        }
      } else {
        val nextPos = fromPos.movedBy(move)
        move(nextPos, move)
        swap(fromPos, nextPos)
      }
      return true
    }

    fun canMove(fromPos: Position, move: Move): Boolean {
      val cell = map.at(fromPos)
      if (cell == EMPTY) return true
      if (cell == WALL) return false
      if (WideBox.isWideBox(cell)) {
        val wideBoxPos = locateWideBox(fromPos)
        val nextWideBoxPos = wideBoxPos.movedBy(move)
        return if (move == Move.LEFT) {
          canMove(nextWideBoxPos.h1, move)
        } else if (move == Move.RIGHT) {
          canMove(nextWideBoxPos.h2, move)
        } else {
          canMove(nextWideBoxPos.h1, move) && canMove(nextWideBoxPos.h2, move)
        }
      }
      return canMove(fromPos.movedBy(move), move)
    }

    fun locateWideBox(p: Position): WideBox {
      return if (map.at(p) == WIDE_BOX_H1) {
        WideBox(p)
      } else {
        WideBox(p.movedBy(Move.LEFT))
      }
    }

    data class WideBox(val h1: Position, val h2: Position = h1.movedBy(Move.RIGHT)) {
      fun movedBy(move: Move): WideBox {
        return WideBox(h1.movedBy(move), h2.movedBy(move))
      }

      companion object {
        fun isWideBox(char: Char): Boolean {
          return char == WIDE_BOX_H1 || char == WIDE_BOX_H2
        }
      }
    }

    override fun boxPositions(): List<Position> {
      return map.data.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, cell -> if (cell == WIDE_BOX_H1) Position(x, y) else null }
      }
    }

    fun swap(p1: Position, p2: Position) {
      val temp = map.at(p1)
      map.set(p1, map.at(p2))
      map.set(p2, temp)
    }
  }

  data class Warehouse(
      val map: WarehouseMap,
      val moves: List<Move>,
      var robotPosition: Position,
      val controller: MoveController = NarrowController(map),
  ) {

    fun withWidenMap(): Warehouse {
      val widenMap = map.widen()
      return Warehouse(widenMap, moves, widenMap.find(ROBOT), WideController(widenMap))
    }

    fun moveRobot() {
      moves.forEach { move -> moveRobot(move) }
    }

    fun moveRobot(move: Move) {
      //      println(move.char)
      if (controller.move(robotPosition, move)) {
        robotPosition = robotPosition.movedBy(move)
      }
      //      map.print()
    }

    fun moveRobot(terminal: Terminal) {
      moves.forEach { move -> moveRobot(move, terminal) }
    }

    fun moveRobot(move: Move, terminal: Terminal) {
      terminal.println(green("${move.char}"))
      if (controller.move(robotPosition, move)) {
        robotPosition = robotPosition.movedBy(move)
      }
      map.print(terminal)
      terminal.cursor.move {
        up(map.height + 2)
        startOfLine()
      }
      Thread.sleep(10)
    }

    fun sumOfGps(): Int {
      return controller.boxPositions().sumOf { it.gps }
    }

    companion object {

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
      val result = warehouse.sumOfGps()
      println("Day15 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val narrowWarehouse = input(inputResourceName)
      val warehouse = narrowWarehouse.withWidenMap()
      val terminal = Terminal()
      warehouse.moveRobot(terminal)
      val result = warehouse.sumOfGps()
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
