package earth.adi.aoc2024

class Day06 {

  data class Position(val x: Int, val y: Int) {
    fun plus(direction: Direction): Position {
      return Position(x + direction.x, y + direction.y)
    }
  }

  enum class Direction(val x: Int, val y: Int, val char: Char) {
    UP(0, -1, '^'),
    RIGHT(1, 0, '>'),
    DOWN(0, 1, 'v'),
    LEFT(-1, 0, '<'),
    ;

    fun turnRight(): Direction {
      return when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
      }
    }

    companion object {
      private val chars = entries.map { it.char }.toSet()
      private val map = entries.associateBy { it.char }

      fun isDirectionChar(char: Char): Boolean {
        return chars.contains(char)
      }

      fun from(char: Char): Direction {
        return map[char] ?: throw IllegalArgumentException("not a Direction: $char")
      }
    }
  }

  data class Area(val map: Map, val guard: Guard) {
    val initialGuardPlacement = Placement(guard.placement.position, guard.placement.direction)

    fun print() {
      (0 until map.width).forEach { y ->
        (0 until map.height).forEach { x ->
          val pos = Position(x, y)
          if (pos == guard.placement.position) {
            print(guard.placement.direction.char)
          } else if (pos == map.additionalObstructionPosition) {
            print(Map.ADDITIONAL_OBSTRUCTION)
          }else if (guard.patrolPath.map { it }.contains(pos)) {
            print(Map.VISITED)
          } else {
            print(map.at(pos))
          }
        }
        println()
      }
      println()
    }

    fun reset() {
      this.map.reset()
      this.guard.reset(initialGuardPlacement)
    }

    companion object {
      fun from(text: String): Area {
        val data = text.split("\n").map { it.toCharArray().toMutableList() }
        val map = Map(data.toMutableList())
        val guard = findGuard(map)
        map.replace(Map.CLEAR, guard.placement.position)
        return Area(map, guard)
      }

      private fun findGuard(map: Map): Guard {
        (0 until map.width).forEach { y ->
          (0 until map.height).forEach { x ->
            val position = Position(x, y)
            val char = map.at(position)
            if (Guard.isGuard(char)) {
              return Guard(Placement(position, Direction.from(char)))
            }
          }
        }
        throw IllegalArgumentException("map without guard?")
      }
    }
  }

  data class Placement(
      val position: Position,
      val direction: Direction,
  ) {
    fun withPosition(position: Position): Placement {
      return Placement(position, direction)
    }

    fun withDirection(direction: Direction): Placement {
      return Placement(position, direction)
    }
  }

  data class Guard(var placement: Placement) {
    val patrolPath: MutableSet<Position> = mutableSetOf()
    val patrolPathHistory: MutableList<Placement> = mutableListOf()

    enum class MoveOutcome {
      OUTSIDE,
      LOOP,
    }

    fun move(map: Map): MoveOutcome {
      while (true) {
        val nextPosition = placement.position.plus(placement.direction)
        if (!map.isInside(nextPosition)) {
          patrolPathHistory.add(placement)
          patrolPath.add(placement.position)
          placement = placement.withPosition(nextPosition)
          return MoveOutcome.OUTSIDE
        }
        if (isLooping()) {
          return MoveOutcome.LOOP
        }
        if (isBlockedAt(map, nextPosition)) {
          placement = placement.withDirection(placement.direction.turnRight())
          continue
        }
        patrolPathHistory.add(placement)
        patrolPath.add(placement.position)
        placement = placement.withPosition(nextPosition)
      }
    }

    private fun isBlockedAt(map: Map, nextPosition: Position) =
        map.at(nextPosition) == Map.OBSTRUCTION || nextPosition == map.additionalObstructionPosition

    private fun isLooping(): Boolean {
      var fast = 0
      for (slow in 0 until patrolPathHistory.size) {
        if (slow != 0) {
          if (fast > patrolPathHistory.size - 1) {
            return false
          }
          if (patrolPathHistory[slow] == patrolPathHistory[fast]) {
            return true
          }
        }
        fast += 2
      }
      return false
    }

    fun reset(placement: Placement) {
      this.patrolPathHistory.clear()
      this.patrolPath.clear()
      this.placement = placement
    }

    companion object {
      fun isGuard(char: Char): Boolean {
        return Direction.isDirectionChar(char)
      }
    }
  }

  data class Map(val data: MutableList<MutableList<Char>>) {
    var additionalObstructionPosition: Position = Position(-1, -1)

    fun isInside(position: Position): Boolean {
      with(position) {
        if (y < 0) return false
        if (y >= height) return false
        if (x < 0) return false
        if (x >= width) return false
      }
      return true
    }

    val height: Int
      get() = data.size

    val width: Int
      get() = data[0].size

    fun at(pos: Position): Char {
      return data[pos.y][pos.x]
    }

    fun replace(replacement: Char, at: Position) {
      data[at.y][at.x] = replacement
    }

    fun reset() {
      additionalObstructionPosition = Position(-1, -1)
    }

    companion object {
      const val CLEAR = '.'
      const val OBSTRUCTION = '#'
      const val ADDITIONAL_OBSTRUCTION = 'O'
      const val VISITED = 'X'
    }
  }

  companion object {
    const val INPUT_DAY_06 = "/day_06.txt"

    fun part1(inputResourceName: String): Int {
      val area = input(inputResourceName)
      area.guard.move(area.map)
      val result = area.guard.patrolPath.size
      println("Day06 part1 = $result")
      return result
    }

    fun part2(inputResourceName: String): Int {
      val area = input(inputResourceName)
      area.guard.move(area.map)
      val result =
          area.guard.patrolPath
              .filter { it != area.initialGuardPlacement.position }
              .filter { position ->
                area.reset()
                area.map.additionalObstructionPosition = position
                val moveOutcome = area.guard.move(area.map)
                //                if (outcome == Guard.MoveOutcome.LOOP) {
                //                  area.print()
                //                }
                moveOutcome == Guard.MoveOutcome.LOOP
              }
              .toSet()
              .count()
      println("Day06 part2 = $result")
      return result
    }

    fun input(resourceName: String): Area {
      val resource =
          Any::class::class.java.getResourceAsStream(resourceName)
              ?: throw IllegalArgumentException("Resource $resourceName not found")
      val text = resource.bufferedReader().readText().trim()
      return Area.from(text)
    }
  }
}
