package earth.adi.aoc2024

import kotlin.math.abs

class Day21 {

  data class Dir(val char: Char, val x: Int, val y: Int) {
    companion object {
      val UP = Dir('^', 0, -1)
      val DOWN = Dir('v', 0, 1)
      val LEFT = Dir('<', -1, 0)
      val RIGHT = Dir('>', 1, 0)

      val ALL = listOf(LEFT, UP, DOWN, RIGHT)
    }
  }

  data class Position(val x: Int, val y: Int) {
    fun plus(vec: Dir) = Position(x + vec.x, y + vec.y)

    fun squareDistanceTo(other: Position) = abs(x - other.x) + abs(y - other.y)

    fun dirTo(position: Position): Dir {
      return when {
        position.x < x -> Dir.LEFT
        position.x > x -> Dir.RIGHT
        position.y < y -> Dir.UP
        position.y > y -> Dir.DOWN
        else -> throw IllegalArgumentException("Same position")
      }
    }
  }

  open class Keypad(val keys: Array<Array<Char>>) {
    private val positionMap =
        keys
            .flatMapIndexed { y, row -> row.mapIndexed { x, c -> c to Position(x, y) } }
            .toMap()
            .filter { it.key != ' ' }

    val codeMoves: MutableMap<String, List<List<String>>> =
        mutableMapOf<String, List<List<String>>>()
    val memoMoves: MutableMap<Move, List<String>> = mutableMapOf<Move, List<String>>()

    fun at(position: Position): Char {
      if (position.x < 0 ||
          position.y < 0 ||
          position.x >= keys[0].size ||
          position.y >= keys.size) {
        return ' '
      }
      return keys[position.y][position.x]
    }

    fun typeWithDirectionalKeypad(code: String): List<List<String>> {
      if (codeMoves.containsKey(code)) {
        return codeMoves[code]!!
      }
      val moves = "A$code".windowed(2).map { s -> moveAlternatives(s[0], s[1]) }
      codeMoves[code] = moves
      return moves
    }

    fun moveAlternatives(fromKey: Char, toKey: Char): List<String> {
      val move = Move(fromKey, toKey)
      if (memoMoves.containsKey(move)) {
        return memoMoves[move]!!
      }
      val fromPos = positionMap[fromKey]!!
      val toPos = positionMap[toKey]!!
      val moves = mutableListOf<List<Position>>()
      moves.add(mutableListOf(fromPos))
      while (true) {
        if (moves.all { it.last() == toPos }) {
          break
        }
        val newMoves = mutableListOf<List<Position>>()
        for (move in moves) {
          val c = move.last()
          val dis = c.squareDistanceTo(toPos)
          Dir.ALL.forEach { dir ->
            val newPos = c.plus(dir)
            if (at(newPos) != ' ') {
              if (newPos.squareDistanceTo(toPos) < dis) {
                newMoves.add(move.plus(newPos))
              }
            }
          }
        }
        moves.clear()
        moves.addAll(newMoves)
      }
      val alternatives =
          moves.map { it.windowed(2) { (from, to) -> from.dirTo(to).char }.joinToString("") + 'A' }
      memoMoves[move] = alternatives
      return alternatives
    }
  }

  data class Move(val fromKey: Char, val toKey: Char)

  class NumericKeypad : Keypad(KEYS) {
    companion object {
      val KEYS =
          arrayOf(
              arrayOf('7', '8', '9'),
              arrayOf('4', '5', '6'),
              arrayOf('1', '2', '3'),
              arrayOf(' ', '0', 'A'),
          )
    }
  }

  class DirectionalKeypad() : Keypad(KEYS) {
    companion object {
      val KEYS =
          arrayOf(
              arrayOf(' ', '^', 'A'),
              arrayOf('<', 'v', '>'),
          )
    }
  }

  class Chain() {
    private val shortestMemo = mutableMapOf<String, Long>()

    private val numericKeypad = NumericKeypad()
    private val directionalKeypad = DirectionalKeypad()

    fun shortestPathLength(code: String, levels: Int, currentLevel: Int = 0): Long {
      val levelCodeKey = "$currentLevel|$code"
      if (shortestMemo.containsKey(levelCodeKey)) {
        return shortestMemo[levelCodeKey]!!
      }
      val keypad = if (currentLevel == 0) numericKeypad else directionalKeypad
      val typed = keypad.typeWithDirectionalKeypad(code)
      if (currentLevel == levels - 1) {
        val length = typed.sumOf { it.minOf { it.length.toLong() } }
        shortestMemo[levelCodeKey] = length
        return length
      }
      val length =
          typed.sumOf { alternatives ->
            alternatives.minOf { typedCode ->
              shortestPathLength(typedCode, levels, currentLevel + 1)
            }
          }
      shortestMemo[levelCodeKey] = length
      return length
    }

    fun complexity(code: String, levels: Int): Long {
      val shortestPathLength = shortestPathLength(code, levels)
      val codeNumber = code.substringBefore('A').removePrefix("0").toLong()
      return codeNumber * shortestPathLength
    }
  }

  companion object {
    const val INPUT_DAY_21 = "/day_21.txt"

    fun part1(inputResourceName: String): Long {
      val codes = input(inputResourceName)
      val result =
          codes.sumOf { code ->
            val chain = Chain()
            chain.complexity(code, 3)
          }
      println("Day21 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Long {
      val codes = input(inputResourceName)
      val result =
          codes.sumOf { code ->
            val chain = Chain()
            chain.complexity(code, 26)
          }
      println("Day21 part2 = $result")
      return result
    }

    fun input(resourceName: String): List<String> {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      return resource.bufferedReader().readLines().filter { it.isNotBlank() }
    }
  }
}
